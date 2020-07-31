package org.tb.gg.di.config

import groovy.util.logging.Log4j


@Log4j
class ServiceConfigReader {
    private static final CONFIG_FILES = ['coreConfig.groovy', 'config.groovy']
    private ServiceMappingRegistry serviceMappingRegistry

    ServiceConfigReader(ServiceMappingRegistry serviceMappingRegistry) {
        this.serviceMappingRegistry = serviceMappingRegistry
    }

    void readServiceConfig() {
        for (String fileName in CONFIG_FILES) {
            registerServiceDefinitionsFromConfigFile(fileName, serviceMappingRegistry)
        }
    }

    private void registerServiceDefinitionsFromConfigFile(String fileName, ServiceMappingRegistry serviceRegistry) {
        def resource = getClass().getClassLoader().getResource(fileName)
        if (resource == null) {
            log.warn("No config file found with name ${fileName}".toString())
            return
        }
        File serviceConfig = new File(
                resource.getFile()
        );
        def binding = new Binding()
        def script = new GroovyShell(binding).parse(serviceConfig)
        script.run()

        def services = (Closure) binding.getVariable('services')
        services.delegate = serviceRegistry

        services()
    }
}
