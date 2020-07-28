package org.tb.gg.di

import org.tb.gg.di.Injected
import org.tb.gg.di.ServiceCreationOrderResolver
import org.tb.gg.di.definition.Service

class SinglePipelineServiceCreationOrderResolver implements ServiceCreationOrderResolver {
    List<List<Class<? extends Service>>> determineCreationOrder(Set<Class<? extends Service>> serviceClasses) {
        Map<Class<? extends Service>, List<Service>> dependenciesPerService = serviceClasses.collectEntries {
            def methods = new ArrayList<>(Arrays.asList(it.getDeclaredMethods()))
            def injectedSetters = methods.findAll { method -> method.isAnnotationPresent(Injected.class) }
            def injectedServices = injectedSetters.collect { setter -> setter.returnType }
            def undeclaredInjectedServices = injectedServices.findAll { injectedService -> !serviceClasses.find { service -> service.getTypeName() == injectedService.getTypeName() } }
            if (undeclaredInjectedServices.size() > 0) {
                throw new IllegalStateException("Found injected property that is itself not declared as a service: ${undeclaredInjectedServices.collect { it.getName() }.join(', ')}")
            }
            [(it): injectedServices]
        }
        // Simple solution for now - order by number of dependencies.
        def orderedServices = dependenciesPerService
                .sort { a, b -> a.getValue().size() <=> b.getValue().size() }
                .collect { clazz, dependencies ->
                    clazz
                }
        return [orderedServices]
    }
}
