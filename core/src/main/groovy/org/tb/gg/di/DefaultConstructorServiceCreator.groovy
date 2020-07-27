package org.tb.gg.di


import org.tb.gg.di.definition.Service

class DefaultConstructorServiceCreator implements ServiceCreator {
    @Override
    List<Service> createServices(Set<Class<? extends Service>> serviceClasses) {
        determineCreationOrder(serviceClasses).collect {
            def serviceInstance = it.getConstructor().newInstance()
            ServiceProvider.setService(serviceInstance)
            serviceInstance
        }
    }

    @Injected
    private List<Class<? extends Service>> determineCreationOrder(Set<Class<? extends Service>> serviceClasses) {
        Map<Class<? extends Service>, List<Service>> dependenciesPerService = serviceClasses.collectEntries {
            def methods = new ArrayList<>(Arrays.asList(it.getDeclaredMethods()))
            def injectedSetters = methods.findAll { method -> method.isAnnotationPresent(Injected.class) }
            injectedSetters.collect { setter -> setter.returnType }
            [(it): injectedSetters]
        }
        // Simple solution for now - order by number of dependencies.
        dependenciesPerService
                .sort { a, b -> a.getValue().size() <=> b.getValue().size() }
                .collect { clazz, dependencies ->
                    clazz
                }
    }
}
