package org.tb.gg.di.config


class ServiceConfigReader {
    private static final CONFIG_FILES = ['coreConfig.groovy', 'config.groovy']
    private ServiceMappingRegistry serviceMappingRegistry

    ServiceConfigReader(ServiceMappingRegistry serviceMappingRegistry) {
        this.serviceMappingRegistry = serviceMappingRegistry
    }

    void readServiceConfig() {
        for(String fileName in CONFIG_FILES) {
            registerServiceDefinitionsFromConfigFile(fileName, serviceMappingRegistry)
        }
    }

    private void registerServiceDefinitionsFromConfigFile(String fileName, ServiceMappingRegistry serviceRegistry) {
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
