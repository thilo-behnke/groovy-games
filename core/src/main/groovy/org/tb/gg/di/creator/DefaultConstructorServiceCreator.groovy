package org.tb.gg.di.creator

import org.tb.gg.di.ServiceCreationOrderResolver
import org.tb.gg.di.ServiceProvider
import org.tb.gg.di.config.ServiceMappingRegistry
import org.tb.gg.di.definition.Service

import java.lang.reflect.Modifier

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
                ServiceProvider.setService(serviceInstance)
                return serviceInstance
            }
        }.flatten()
    }
}
