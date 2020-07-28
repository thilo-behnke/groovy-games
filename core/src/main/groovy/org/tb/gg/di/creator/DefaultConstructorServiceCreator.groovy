package org.tb.gg.di.creator

import org.tb.gg.di.ServiceCreationOrderResolver
import org.tb.gg.di.ServiceProvider
import org.tb.gg.di.definition.Service

class DefaultConstructorServiceCreator implements ServiceCreator {
    private ServiceCreationOrderResolver serviceCreationOrderResolver

    DefaultConstructorServiceCreator(ServiceCreationOrderResolver serviceCreationOrderResolver) {
        this.serviceCreationOrderResolver = serviceCreationOrderResolver
    }

    @Override
    List<Service> createServices(Set<Class<? extends Service>> serviceClasses) {
        (List<Service>) serviceCreationOrderResolver.determineCreationOrder(serviceClasses).collect { pipe ->
            // TODO: Could each be done in separate threads.
            pipe.collect { service ->
                def serviceInstance = service.getConstructor().newInstance()
                ServiceProvider.setService(serviceInstance)
                return serviceInstance
            }
        }.flatten()
    }
}
