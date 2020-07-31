package org.tb.gg.di.config


class ServiceConfigReader {
    private class ServiceRegistry {
        Map<String, Class<?>> registeredServices = new HashMap<>()
        @Override
        Object invokeMethod(String name, Object args) {
            Object[] argArray;
            if (args instanceof Object[]) {
                argArray = (Object[]) args;
            } else {
                argArray = new Object[]{args};
            }

            if(!(argArray[0] instanceof Class)) {
                return super.invokeMethod(name, args)
            }

            def interfaceName = name.capitalize()
            registeredServices[interfaceName] = (Class<?>) argArray[0]
            return null
        }
    }

    Map<String, Class> readServiceConfig() {
        File serviceConfig = new File(
                getClass().getClassLoader().getResource("config.groovy").getFile()
        );
        def binding = (Binding) new GroovyShell().evaluate(serviceConfig)
        def services = (Closure) binding.getVariable('services')

        def serviceRegistry = new ServiceRegistry()
        services.delegate = serviceRegistry

        services()

        serviceRegistry.getRegisteredServices()
    }
}
