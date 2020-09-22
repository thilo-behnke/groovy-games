package org.tb.gg.di.scanner

import com.google.common.reflect.ClassPath
import org.tb.gg.di.config.ServiceConfigReader
import org.tb.gg.di.definition.Service

abstract class ClasspathServiceScanner implements ServiceScanner {
    final ClassLoader loader
    final ServiceConfigReader serviceConfigReader

    ClasspathServiceScanner(ServiceConfigReader serviceConfigReader) {
        // TODO: Issue when multithreading?
        this.loader = Thread.currentThread().getContextClassLoader();
        this.serviceConfigReader = serviceConfigReader
    }

    protected Set<Class<? extends Service>> scanForServices(Class<? extends Service> subClass) {
        subClass = subClass ?: Service.class
        def services = findAllServicesInClassPath()
        findServicesAssignableFromSubClass(services, subClass)
    }

    private Set<Class<? extends Service>> findAllServicesInClassPath() {
        // TODO: This should also work for other packages.
        def relevantPackages = serviceConfigReader.packagesToScan
        ClassPath.from(loader).getTopLevelClasses()
                .findAll { classInfo -> relevantPackages.any { pkg -> classInfo.getPackageName().startsWith(pkg) } }
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
