package org.tb.gg.di.config


class ServiceConfigReader {
    private static final CONFIG_FILES = ['coreConfig.groovy', 'config.groovy']

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
        def serviceRegistry = new ServiceRegistry()

        for(String fileName in CONFIG_FILES) {
            registerServiceDefinitionsFromConfigFile(fileName, serviceRegistry)
        }

        return serviceRegistry.getRegisteredServices()
    }

    private void registerServiceDefinitionsFromConfigFile(String fileName, ServiceRegistry serviceRegistry) {
        File serviceConfig = new File(
                getClass().getClassLoader().getResource(fileName).getFile()
        );
        def binding = new Binding()
        def script = new GroovyShell(binding).parse(serviceConfig)
        script.run()

        def services = (Closure) binding.getVariable('services')
        services.delegate = serviceRegistry

        services()
    }
}
