package org.tb.gg.di.validation

import org.tb.gg.di.config.ServiceConfigurationException
import org.tb.gg.di.config.ServiceMappingRegistry
import org.tb.gg.di.definition.Service

import java.lang.reflect.Modifier

class ServiceImplementationValidator {

    private ServiceMappingRegistry serviceMappingRegistry

    ServiceImplementationValidator(ServiceMappingRegistry serviceMappingRegistry) {
        this.serviceMappingRegistry = serviceMappingRegistry
    }

    Set<Class<? extends Service>> validateServicesAndReplaceInterfaces(Set<Class<? extends Service>> services) {
        throwIfMatchingServiceImplementationExistsForInterface(services)
        replaceInterfacesWithImplementations(services)
    }

    static void throwIfMatchingServiceImplementationExistsForInterface(Set<Class<? extends Service>> services) {
        services
                .findAll { service ->
                    service.isInterface() || Modifier.isAbstract(service.getModifiers())
                }
                .find { serviceInterface ->
                    services.find { it != serviceInterface && serviceInterface.isAssignableFrom(it) }
                }

    }

    private replaceInterfacesWithImplementations(Set<Class<? extends Service>> services) {
        services.collect { serviceClass ->
            if (serviceClass.isInterface() || Modifier.isAbstract(serviceClass.getModifiers())) {
                def implementation = serviceMappingRegistry.getImplementationForBaseClass(serviceClass.getSimpleName())
                if (implementation == null) {
                    throw new ServiceConfigurationException("No implementation found for ${serviceClass.getName()}".toString())
                }
                return implementation
            }
            return serviceClass
        }
    }
}
