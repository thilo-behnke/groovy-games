package org.tb.gg.di

import groovy.util.logging.Log4j
import org.tb.gg.di.definition.Service
import org.tb.gg.di.definition.Singleton
import org.tb.gg.di.scanner.ClasspathServiceScanner

@Log4j
class DependencyInjectionHandler {

    private isInitialized = false

    Service getService(String name) {
        return (Service) ServiceProvider.getService(name)
    }

    List<Service> injectDependencies() {
        if(isInitialized) {
            log.warn("Tried to inject dependencies again.")
            return
        }
        def singletonClasses = new ClasspathServiceScanner().scanForServices(Singleton.class)
        def services = new DefaultConstructorServiceCreator().createServices(singletonClasses)

        isInitialized = true

        return services
    }
}
