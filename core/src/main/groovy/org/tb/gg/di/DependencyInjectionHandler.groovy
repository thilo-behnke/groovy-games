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

    Service getService(String name) {
        return (Service) ServiceProvider.getSingletonService(name)
    }

    List<Service> injectDependencies() {
        if (isInitialized) {
            log.warn("Tried to inject dependencies again.")
            return []
        }
        def serviceMappingRegistry = new ServiceMappingRegistry()
        new ServiceConfigReader(new DefaultResourceProvider(), serviceMappingRegistry).readConfigAndRegisterServices()

        def singletonClasses = findSingletonClasses()
        def multiInstanceServiceClasses = new ClasspathMultiInstanceServiceScanner().scanForServices()
        def validatedSingletonClasses = new ServiceImplementationValidator(serviceMappingRegistry).validateServicesAndReplaceInterfaces(singletonClasses)
        def serviceClasses = validatedSingletonClasses + multiInstanceServiceClasses

        def serviceInstances = new DefaultConstructorServiceCreator(new SinglePipelineServiceCreationOrderResolver(), serviceMappingRegistry).createServices(serviceClasses)

        isInitialized = true

        return serviceInstances
    }

    private static Set<Class<? extends Singleton>> findSingletonClasses() {
        return (Set<Class<? extends Singleton>>) new ClasspathSingletonScanner().scanForServices().findAll { !ServiceProvider.hasSingletonImplementation(it.simpleName) }
    }
}
