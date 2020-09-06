package org.tb.gg.di.validation

import org.tb.gg.di.config.ServiceConfigurationException
import org.tb.gg.di.config.ServiceMappingRegistry
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ServiceImplementationValidatorSpec extends Specification {
    interface SomeInterface {}

    abstract class SomeAbstractClass {}

    class SomeClass {}

    class SomeOtherClass {}

    class SomeClassImplementingInterface implements SomeInterface {}

    class SomeClassAlsoImplementingInterface implements SomeInterface {}

    class SomeClassExtendingAbstractClass extends SomeAbstractClass {}

    ServiceImplementationValidator serviceImplementationValidator
    ServiceMappingRegistry serviceMappingRegistry

    void setup() {
        serviceMappingRegistry = Mock(ServiceMappingRegistry)
        serviceImplementationValidator = new ServiceImplementationValidator(serviceMappingRegistry)
    }

    def 'should return an empty set of services when provided an empty set'() {
        given:
        def services = (Set<Class>) []
        when:
        def res = serviceImplementationValidator.validateServicesAndReplaceInterfaces(services)
        then:
        res == services
    }

    def 'should return classes unchanged'() {
        given:
        def services = (Set<Class>) [SomeClass.class, SomeOtherClass.class]
        when:
        def res = serviceImplementationValidator.validateServicesAndReplaceInterfaces(services)
        then:
        res == services
    }

    def 'should throw an exception if a service is provided as an interface but there is no implementation'() {
        given:
        def services = (Set<Class>) [SomeInterface.class]
        when:
        serviceImplementationValidator.validateServicesAndReplaceInterfaces(services)
        then:
        thrown ServiceConfigurationException
    }

    def 'should throw an exception if a service is provided as an abstract class but there is no implementation'() {
        given:
        def services = (Set<Class>) [SomeAbstractClass.class]
        when:
        serviceImplementationValidator.validateServicesAndReplaceInterfaces(services)
        then:
        thrown ServiceConfigurationException
    }

    def 'should replace the service interface with the correct implementation provided by the service mapping registry'() {
        given:
        serviceMappingRegistry.getServiceInstanceForBaseClass(SomeInterface.getSimpleName()) >> SomeClassImplementingInterface
        def services = (Set<Class>) [SomeClassImplementingInterface.class]
        when:
        def res = serviceImplementationValidator.validateServicesAndReplaceInterfaces(services)
        then:
        res == (Set<Class>) [SomeClassImplementingInterface.class]
    }

    def 'should replace the abstract service class with the correct implementation provided by the service mapping registry'() {
        given:
        serviceMappingRegistry.getServiceInstanceForBaseClass(SomeAbstractClass.getSimpleName()) >> SomeClassExtendingAbstractClass
        def services = (Set<Class>) [SomeAbstractClass.class]
        when:
        def res = serviceImplementationValidator.validateServicesAndReplaceInterfaces(services)
        then:
        res == (Set<Class>) [SomeClassExtendingAbstractClass.class]
    }

    def 'should throw if both a service interface and its implementation is provided in the set of classes'() {
        given:
        def services = (Set<Class>) [SomeInterface.class, SomeClassImplementingInterface.class]
        when:
        serviceImplementationValidator.validateServicesAndReplaceInterfaces(services)
        then:
        thrown ServiceConfigurationException
    }

}
