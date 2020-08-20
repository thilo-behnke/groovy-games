package org.tb.gg.di.creator

import org.tb.gg.di.ServiceCreationOrderResolver
import org.tb.gg.di.ServiceProvider
import org.tb.gg.di.config.ServiceMappingRegistry
import org.tb.gg.di.definition.MultiInstanceService
import org.tb.gg.di.definition.Service
import org.tb.gg.di.definition.Singleton

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
                if (Singleton.isAssignableFrom(serviceClass)) {
                    registerSingleton((Singleton) serviceInstance)
                } else if (MultiInstanceService.isAssignableFrom(serviceClass)) {
                    registerMultiInstanceService((MultiInstanceService) serviceInstance)
                } else {
                    throw new IllegalArgumentException("Unknown service implementation provided, don't know how to register: ${serviceClass}")
                }
                return serviceInstance
            }
        }.flatten()
    }

    private getBaseClassNameIfServiceImplementation(Class<? extends Service> serviceClass) {
        return serviceMappingRegistry.getRegisteredServices().inverse().get(serviceClass)
    }

    private registerSingleton(Singleton singleton) {
        def baseServiceClassName = getBaseClassNameIfServiceImplementation(singleton.class)
        if (baseServiceClassName) {
            ServiceProvider.registerSingletonService(singleton, baseServiceClassName)
        } else {
            ServiceProvider.registerSingletonService(singleton)
        }
    }

    private registerMultiInstanceService(MultiInstanceService multiInstanceService) {
        def multiInstanceInterface = multiInstanceService.class.getInterfaces()
                .findAll { MultiInstanceService.isAssignableFrom(it) }
                .collect { new Tuple2<Class<?>, Integer>(it, it.getInterfaces().size()) }
                .sort { a, b -> a.getV2() <=> b.getV2() }
                .collect { it.getV1() }
                .first()
        ServiceProvider.registerMultiInstanceService(multiInstanceService, multiInstanceInterface.getSimpleName())
    }
}
