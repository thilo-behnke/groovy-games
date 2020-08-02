package org.tb.gg.di.config

import spock.lang.Specification
import spock.lang.Unroll

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
        serviceConfigReader.readConfigAndRegisterServices()
        then:
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    def 'config file exists, but no services closure'() {
        given:
        stubServiceConfigFile('configWithoutServiceDeclaration.groovy')
        when:
        serviceConfigReader.readConfigAndRegisterServices()
        then:
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    def 'config file exists, but empty services closure'() {
        given:
        stubServiceConfigFile('configWithEmptyServiceDeclaration.groovy')
        when:
        serviceConfigReader.readConfigAndRegisterServices()
        then:
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    def 'single service declaration in config'() {
        given:
        stubServiceConfigFile('configWithSingleServiceDeclaration.groovy')
        when:
        serviceConfigReader.readConfigAndRegisterServices()
        then:
        1 * serviceMappingRegistry.invokeMethod('myService', [String.class])
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    def 'multiple service declarations in config'() {
        given:
        stubServiceConfigFile('configWithMultipleServiceDeclarations.groovy')
        when:
        serviceConfigReader.readConfigAndRegisterServices()
        then:
        1 * serviceMappingRegistry.invokeMethod('myService', [String.class])
        1 * serviceMappingRegistry.invokeMethod('myOtherService', [Long.class])
        1 * serviceMappingRegistry.invokeMethod('andAThirdService', [Integer.class])
        0 * serviceMappingRegistry.invokeMethod(*_)
    }

    private stubServiceConfigFile(String fileName) {
        def configFile = loadConfigFile(fileName)
        resourceProvider.getResourceFile('config.groovy') >> configFile
    }

    private loadConfigFile(String fileName) {
        def resource = getClass().getClassLoader().getResource(fileName)
        new File(resource.getFile());
    }


}
