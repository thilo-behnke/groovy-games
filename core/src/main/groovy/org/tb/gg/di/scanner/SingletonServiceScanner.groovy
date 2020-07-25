package org.tb.gg.di.scanner

import com.google.common.reflect.ClassPath
import org.tb.gg.di.definition.Service
import org.tb.gg.di.definition.Singleton

class SingletonServiceScanner implements ServiceScanner {
    final ClassLoader loader

    SingletonServiceScanner() {
        this.loader = Thread.currentThread().getContextClassLoader();
    }

    @Override
    Set<Class<? extends Service>> scanForServices() {
        ClassPath.from(loader).getTopLevelClasses()
                .findAll { it.getPackageName().startsWith("org.tb.gg") }
                .collect {
                    Class.forName(it.getName(), true, loader)
                }
                .findAll {
                    it.getName() != Singleton.class.getName() && Singleton.isAssignableFrom(it)
                }
                .collect {
                    (Class<? extends Singleton>) it
                }
    }
}
