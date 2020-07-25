package org.tb.gg.di.scanner

import com.google.common.reflect.ClassPath
import org.tb.gg.di.definition.Service

class ClasspathServiceScanner implements ServiceScanner {
    final ClassLoader loader

    private Set<Class<? extends Service>> services

    ClasspathServiceScanner() {
        this.loader = Thread.currentThread().getContextClassLoader();
    }

    @Override
    Set<Class<? extends Service>> scanForServices(Class<? extends Service> subClass) {
        subClass = subClass ?: Service.class
        services = ClassPath.from(loader).getTopLevelClasses()
                .findAll { it.getPackageName().startsWith("org.tb.gg") }
                .collect {
                    Class.forName(it.getName(), true, loader)
                }
                .collect {
                    (Class<? extends Service>) it
                }

        services.findAll {
            subClass.isAssignableFrom(it) && it.getName() != subClass.getName()
        }
    }
}
