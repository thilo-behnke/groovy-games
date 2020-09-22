package org.tb.gg.di.scanner

import com.google.common.reflect.ClassPath
import org.tb.gg.di.definition.Service

abstract class ClasspathServiceScanner implements ServiceScanner {
    final ClassLoader loader

    ClasspathServiceScanner() {
        // TODO: Issue when multithreading?
        this.loader = Thread.currentThread().getContextClassLoader();
    }

    protected Set<Class<? extends Service>> scanForServices(Class<? extends Service> subClass) {
        subClass = subClass ?: Service.class
        def services = findAllServicesInClassPath()
        findServicesAssignableFromSubClass(services, subClass)
    }

    private Set<Class<? extends Service>> findAllServicesInClassPath() {
        // TODO: This should also work for other packages.
        ClassPath.from(loader).getTopLevelClasses()
                .findAll { it.getPackageName().startsWith("org.tb.gg") }
                .collect {
                    Class.forName(it.getName(), true, loader)
                }
                .findAll {
                    Service.isAssignableFrom(it)
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
}
