package org.tb.gg.di

import groovy.util.logging.Log4j
import org.tb.gg.di.config.DefaultResourceProvider
import org.tb.gg.di.config.ServiceConfigReader
import org.tb.gg.di.config.ServiceMappingRegistry
import org.tb.gg.di.creator.DefaultConstructorServiceCreator
import org.tb.gg.di.definition.Service
import org.tb.gg.di.definition.Singleton
import org.tb.gg.di.scanner.ClasspathMultiInstanceServiceScanner
import org.tb.gg.di.scanner.ClasspathSingletonScanner
import org.tb.gg.di.validation.ServiceImplementationValidator

@Log4j
class DependencyInjectionHandler {

    private isInitialized = false
    private ServiceConfigReader serviceConfigReader

    Service getService(String name) {
        return (Service) ServiceProvider.getSingletonService(name)
    }

    List<Service> injectDependencies() {
        if (isInitialized) {
            log.warn("Tried to inject dependencies again.")
            return []
        }
        def serviceMappingRegistry = new ServiceMappingRegistry()
        serviceConfigReader = new ServiceConfigReader(new DefaultResourceProvider(), serviceMappingRegistry)
        serviceConfigReader.readConfigFiles()

        def providedSingletonInstances = findProvidedSingletonInstances(serviceMappingRegistry)
        def createdServiceInstances = createSingletonServiceInstances(serviceMappingRegistry)

        def allServiceInstances = createdServiceInstances + providedSingletonInstances
        isInitialized = true

        return allServiceInstances.toList()
    }

    private Set<Service> createSingletonServiceInstances(ServiceMappingRegistry serviceMappingRegistry) {
        def singletonClasses = findSingletonClassesToInstantiate(serviceMappingRegistry)
        def multiInstanceServiceClasses = new ClasspathMultiInstanceServiceScanner(serviceConfigReader).scanForServices()
        def validatedSingletonClasses = new ServiceImplementationValidator(serviceMappingRegistry).validateServicesAndReplaceInterfaces(singletonClasses)
        def serviceClasses = validatedSingletonClasses + multiInstanceServiceClasses
        return new DefaultConstructorServiceCreator(new SinglePipelineServiceCreationOrderResolver(), serviceMappingRegistry).createServices(serviceClasses)
    }

    private Set<Class<? extends Singleton>> findSingletonClassesToInstantiate(ServiceMappingRegistry serviceMappingRegistry) {
        return (Set<Class<? extends Singleton>>) new ClasspathSingletonScanner(serviceConfigReader)
                .scanForServices().findAll { !serviceMappingRegistry.getServiceInstanceForBaseClass(it.simpleName) }
    }

    private Set<Service> findProvidedSingletonInstances(ServiceMappingRegistry serviceMappingRegistry) {
        def serviceInstances = (Set<Tuple2<String, Service>>) new ClasspathSingletonScanner(serviceConfigReader).scanForServices()
                .collect {
                    def serviceInstance = serviceMappingRegistry.getServiceInstanceForBaseClass(it.simpleName)
                    if (serviceInstance) {
                        return new Tuple2<String, Service>(it.simpleName, serviceInstance)
                    }
                    return null
                }
                .findAll { it }
        serviceInstances.each { ServiceProvider.registerSingletonService(it.getV2(), it.getV1()) }
        return serviceInstances.collect { it.getV2() }
    }
}
