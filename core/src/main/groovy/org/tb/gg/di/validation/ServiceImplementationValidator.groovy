package org.tb.gg.di.validation

import org.tb.gg.di.config.ServiceMappingRegistry
import org.tb.gg.di.definition.Service

import java.lang.reflect.Modifier

class ServiceImplementationValidator {

    private ServiceMappingRegistry serviceMappingRegistry

    ServiceImplementationValidator(ServiceMappingRegistry serviceMappingRegistry) {
        this.serviceMappingRegistry = serviceMappingRegistry
    }

    Set<Class<? extends Service>> validateServicesAndReplaceInterfaces(Set<Class<? extends Service>> services) {
        validateServiceImplementationsAndThrowOnError(services)

        services.collect { serviceClass ->
            if (serviceClass.isInterface() || Modifier.isAbstract(serviceClass.getModifiers())) {
                return serviceMappingRegistry.getImplementationForBaseClass(serviceClass.getSimpleName())
            }
            return serviceClass
        }
    }

    private validateServiceImplementationsAndThrowOnError(Set<Class<? extends Service>> services) {
        services
                .findAll { it.isInterface() || Modifier.isAbstract(it.getModifiers()) }
                .findAll { service ->
                    def implementationsOfService = services.findAll { service.isAssignableFrom(it) }
                    if (implementationsOfService.size() > 1) {
                        throw new IllegalStateException("Conflicting implementations of service ${service.getName()}: ${implementationsOfService.collect { it.getName() }.join(', ')}".toString())
                    } else if (implementationsOfService.size() == 0) {
                        throw new IllegalStateException("No implementation found for ${service.getName()}".toString())
                    }
                }
    }

    private replaceInterfaces(Set<Class<? extends Service>> services) {
        services.collect { serviceClass ->
            if (serviceClass.isInterface() || Modifier.isAbstract(serviceClass.getModifiers())) {
                return serviceMappingRegistry.getImplementationForBaseClass(serviceClass.getSimpleName())
            }
            return serviceClass
        }
    }
}
