package org.tb.gg.di.injector

import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Service
import org.tb.gg.di.scanner.ServiceScanner

class ReflectionServiceInjector implements ServiceInjector {
    private ServiceScanner serviceScanner

    ReflectionServiceInjector(ServiceScanner serviceScanner) {
        this.serviceScanner = serviceScanner
    }

    @Override
    void injectServices(List<Service> services) {
        services.each {
            def dependentServices = findDependentServices(it)
        }
    }

    private findDependentServices(Service service) {
        serviceScanner.scanForServices()
                .findAll { it.getName() != service.class.name }
                .findAll {
                    isClassDependentOnService(it, service)
                }
    }

    private static isClassDependentOnService(Class<?> clazz, Service service) {
        def fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()))
        def matchingServiceProperty = fields.find { field ->
            if (!field.getAnnotation(Inject)) {
                return false
            }
            def fieldName = field.getName()
            fieldName.capitalize() == service.getClass().getName()
        }
        return matchingServiceProperty
    }
}
