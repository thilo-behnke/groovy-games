package org.tb.gg.di.config

import org.apache.commons.io.FileUtils
import spock.lang.Specification
import spock.lang.Unroll

import java.nio.charset.StandardCharsets
import java.nio.file.Files

@Unroll
class ServiceConfigReaderSpec extends Specification {

    ServiceConfigReader serviceConfigReader
    ServiceMappingRegistry serviceMappingRegistry
    ResourceProvider resourceProvider

    void setup() {
        serviceMappingRegistry = Mock(ServiceMappingRegistry)
        resourceProvider = Mock(ResourceProvider)
        serviceConfigReader = new ServiceConfigReader(resourceProvider, serviceMappingRegistry)
    }

    def 'no config file exists'() {
        when:
        serviceConfigReader.readConfigFiles()
        then:
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    def 'config file exists, but no services closure'() {
        given:
        stubServiceConfigFile('configWithoutServiceDeclaration.groovy')
        when:
        serviceConfigReader.readConfigFiles()
        then:
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    def 'config file exists, but empty services closure'() {
        given:
        stubServiceConfigFile('configWithEmptyServiceDeclaration.groovy')
        when:
        serviceConfigReader.readConfigFiles()
        then:
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    def 'single service declaration in config'() {
        given:
        stubServiceConfigFile('configWithSingleServiceDeclaration.groovy')
        when:
        serviceConfigReader.readConfigFiles()
        then:
        1 * serviceMappingRegistry.invokeMethod('myService', [String.class])
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    def 'multiple service declarations in config'() {
        given:
        stubServiceConfigFile('configWithMultipleServiceDeclarations.groovy')
        when:
        serviceConfigReader.readConfigFiles()
        then:
        1 * serviceMappingRegistry.invokeMethod('myService', [String.class])
        1 * serviceMappingRegistry.invokeMethod('myOtherService', [Long.class])
        1 * serviceMappingRegistry.invokeMethod('andAThirdService', [Integer.class])
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    private stubServiceConfigFile(String fileName) {
        def configFile = loadConfigFile(fileName)
        resourceProvider.getResourceFileContent('config.groovy') >> configFile
    }

    private loadConfigFile(String fileName) {
        def resource = getClass().getClassLoader().getResource(fileName)
        FileUtils.readFileToString(new File(resource.getFile()), StandardCharsets.UTF_8)
    }
}
