package org.tb.gg.di

import org.tb.gg.di.definition.Service

class SinglePipelineServiceCreationOrderResolver implements ServiceCreationOrderResolver {
    List<List<Class<? extends Service>>> determineCreationOrder(Set<Class<? extends Service>> serviceClasses) {

        Map<Class<? extends Service>, List<Class<? extends Service>>> servicesWithDependencies = identifyServiceDependencies(serviceClasses)
        def pipe = createOrderedPipelineBasedOnDependencies(servicesWithDependencies)

        return [pipe]
    }

    private static Map<Class<? extends Service>, List<Class<? extends Service>>> identifyServiceDependencies(Set<Class<? extends Service>> serviceClasses) {
        serviceClasses.collectEntries {
            def methods = new ArrayList<>(Arrays.asList(it.getDeclaredMethods()))
            def injectedSetters = methods.findAll { method -> method.isAnnotationPresent(Injected.class) }
            def injectedServices = injectedSetters.collect { setter -> setter.returnType }
            def undeclaredInjectedServices = identifyUndeclaredServices(injectedServices, serviceClasses)
            if (undeclaredInjectedServices.size() > 0) {
                throw new IllegalStateException("Found injected property that is itself not declared as a service: ${undeclaredInjectedServices.collect { it.getName() }.join(', ')}")
            }
            [(it): injectedServices]
        }
    }

    private static identifyUndeclaredServices(List<Class<? extends Service>> injectedServices, Set<Class<? extends Service>> allServiceClasses) {
        injectedServices.findAll { injectedService -> !allServiceClasses.find { service -> service.getTypeName() == injectedService.getTypeName() } }
    }

    private static createOrderedPipelineBasedOnDependencies(Map<Class<? extends Service>, List<Class<? extends Service>>> servicesWithDependencies) {
        def orderedServices = servicesWithDependencies
                .sort { a, b -> a.getValue().size() <=> b.getValue().size() }

        def pipe = new ArrayList<Class<Service>>()
        for (Map.Entry<Class<? extends Service>, List<Class<? extends Service>>> serviceWithDependencies : orderedServices.entrySet()) {
            def serviceClass = serviceWithDependencies.key
            def dependencies = serviceWithDependencies.value

            if (pipe.contains(serviceClass)) {
                continue
            }

            if (dependencies.size() == 0) {
                pipe.add(serviceClass)
            }

            pipe.addAll(dependencies)
            pipe.add(serviceClass)
        }

        pipe.unique()
    }
}
