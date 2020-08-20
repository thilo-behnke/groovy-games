package org.tb.gg.di.creator

import org.tb.gg.di.ServiceCreationOrderResolver
import org.tb.gg.di.ServiceProvider
import org.tb.gg.di.config.ServiceMappingRegistry
import org.tb.gg.di.definition.MultiInstanceService
import org.tb.gg.di.definition.Service

class DefaultConstructorServiceCreator implements ServiceCreator {
    private ServiceCreationOrderResolver serviceCreationOrderResolver
    private ServiceMappingRegistry serviceMappingRegistry

    DefaultConstructorServiceCreator(ServiceCreationOrderResolver serviceCreationOrderResolver, ServiceMappingRegistry serviceMappingRegistry) {
        this.serviceCreationOrderResolver = serviceCreationOrderResolver
        this.serviceMappingRegistry = serviceMappingRegistry
    }

    @Override
    List<Service> createServices(Set<Class<? extends Service>> serviceClasses) {
        (List<Service>) serviceCreationOrderResolver.determineCreationOrder(serviceClasses).collect { pipe ->
            // TODO: Could each be done in separate threads.
            pipe.collect { serviceClass ->
                def serviceInstance = serviceClass.getConstructor().newInstance()
                if (org.tb.gg.di.definition.Singleton.isAssignableFrom(serviceClass)) {
                    def baseServiceClassName = getBaseClassNameIfServiceImplementation(serviceClass)
                    if (baseServiceClassName) {
                        ServiceProvider.registerSingletonService(serviceInstance, baseServiceClassName)
                    } else {
                        ServiceProvider.registerSingletonService(serviceInstance)
                    }
                } else if (MultiInstanceService.isAssignableFrom(serviceClass)) {
                    // TODO: Not a great solution, but works for now...
                    def multiInstanceInterface = serviceClass.getInterfaces().find { MultiInstanceService.isAssignableFrom(it) }
                    System.out.println(multiInstanceInterface)
                    ServiceProvider.registerMultiInstanceService(serviceInstance, multiInstanceInterface.getSimpleName())
                }
                return serviceInstance
            }
        }.flatten()
    }

    private getBaseClassNameIfServiceImplementation(Class<? extends Service> serviceClass) {
        return serviceMappingRegistry.getRegisteredServices().inverse().get(serviceClass)
    }
}
