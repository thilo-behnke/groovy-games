package org.tb.gg.di.scanner

import com.google.common.reflect.ClassPath
import org.tb.gg.di.definition.Service

class ClasspathServiceScanner implements ServiceScanner {
    final ClassLoader loader

    ClasspathServiceScanner() {
        this.loader = Thread.currentThread().getContextClassLoader();
    }

    @Override
    Set<Class<? extends Service>> scanForServices(Class<? extends Service> subClass) {
        subClass = subClass ?: Service.class
        def services = findAllServicesInClassPath()
        def implementingServices = findServicesAssignableFromSubClass(services, subClass)
        removeConcreteServiceImplementations(implementingServices)
    }

    private Set<Class<? extends Service>> findAllServicesInClassPath() {
        ClassPath.from(loader).getTopLevelClasses()
                .findAll { it.getPackageName().startsWith("org.tb.gg") }
                .collect {
                    Class.forName(it.getName(), true, loader)
                }
                .collect {
                    (Class<? extends Service>) it
                }
    }

    private static Set<Class<? extends Service>> findServicesAssignableFromSubClass(Set<Class<? extends Service>> services, Class<? extends Service> subClass) {
        services.findAll {
            subClass.isAssignableFrom(it) && it.getName() != subClass.getName()
        }
    }

    private static Set<Class<? extends Service>> removeConcreteServiceImplementations(Set<Class<? extends Service>> services) {
        services.findAll{!services.find{service -> it != service && service.isAssignableFrom(it)}}
    }
}
