package org.tb.gg.di.config

import groovy.util.logging.Log4j


@Log4j
class ServiceConfigReader {
    /**
     * The order of the config files defines their priority: The last definition found will be valid, all previous ones forgotten.
     */
    private static final CONFIG_FILES = ['coreConfig.groovy', 'config.groovy']
    private static final SCAN_PACKAGES_KEY = 'scanPackages'
    private ResourceProvider resourceProvider
    private ServiceMappingRegistry serviceMappingRegistry

    List<String> packagesToScan

    ServiceConfigReader(ResourceProvider resourceProvider, ServiceMappingRegistry serviceMappingRegistry) {
        this.resourceProvider = resourceProvider
        this.serviceMappingRegistry = serviceMappingRegistry
    }

    void readConfigFiles() {
        for (String fileName in CONFIG_FILES) {
            registerServiceDefinitionsFromConfigFile(fileName, serviceMappingRegistry)
        }

        Set<String> uniqPackages = []
        for (String fileName in CONFIG_FILES) {
            def serviceConfig = resourceProvider.getResourceFileContent(fileName)
            if (!serviceConfig) {
                continue
            }

            def binding = new Binding()
            def script = new GroovyShell(binding).parse(serviceConfig)
            script.run()

            if (!binding.hasVariable(SCAN_PACKAGES_KEY)) {
                continue
            }
            def scanPackages = (List<String>) binding.getVariable(SCAN_PACKAGES_KEY)
            uniqPackages.addAll(scanPackages)
        }
        packagesToScan = uniqPackages.toList()
    }

    private void registerServiceDefinitionsFromConfigFile(String fileName, ServiceMappingRegistry serviceRegistry) {
        def serviceConfig = resourceProvider.getResourceFileContent(fileName)
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
