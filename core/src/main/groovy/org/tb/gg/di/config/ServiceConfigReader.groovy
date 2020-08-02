package org.tb.gg.di.config

import groovy.util.logging.Log4j


@Log4j
class ServiceConfigReader {
    private static final CONFIG_FILES = ['coreConfig.groovy', 'config.groovy']
    private ResourceProvider resourceProvider
    private ServiceMappingRegistry serviceMappingRegistry

    ServiceConfigReader(ResourceProvider resourceProvider, ServiceMappingRegistry serviceMappingRegistry) {
        this.resourceProvider = resourceProvider
        this.serviceMappingRegistry = serviceMappingRegistry
    }

    void readConfigAndRegisterServices() {
        for (String fileName in CONFIG_FILES) {
            registerServiceDefinitionsFromConfigFile(fileName, serviceMappingRegistry)
        }
    }

    private void registerServiceDefinitionsFromConfigFile(String fileName, ServiceMappingRegistry serviceRegistry) {
        def serviceConfig = resourceProvider.getResourceFile(fileName)
        if (serviceConfig == null) {
            log.warn("No config file found with name ${fileName}".toString())
            return
        }
        def binding = new Binding()
        def script = new GroovyShell(binding).parse(serviceConfig)
        script.run()

        try {
            def services = (Closure) binding.getVariable('services')
            services.delegate = serviceRegistry
            services()
        } catch (MissingPropertyException ignored) {
            log.warn("No services closure in file ${fileName}".toString())
        }
    }
}
