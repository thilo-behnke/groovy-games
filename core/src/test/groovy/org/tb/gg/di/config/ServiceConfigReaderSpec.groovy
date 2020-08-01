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

    def 'should terminate without registering a service if no config file exists'() {
        when:
        serviceConfigReader.readConfigAndRegisterServices()
        then:
        0 * serviceMappingRegistry.invokeMethod(_)
    }


}
