package org.tb.gg.di.scanner

import org.tb.gg.di.Service
import org.tb.gg.di.Singleton
import org.tb.gg.di.provider.ClassDefinition

class SingletonServiceScanner implements ServiceScanner {

    @Override
    Set<Class<? extends Service>> scanForServices(Set<ClassDefinition> classDefinitions) {
        classDefinitions
                .findAll { it.getPackageName().startsWith("org.tb.gg") }
                .collect {
                    Class.forName(it.getClassName())
                }
                .findAll {
                    it.getName() != Singleton.class.getName() && Singleton.isAssignableFrom(it)
                }
                .collect {
                    (Class<? extends Singleton>) it
                }
    }
}
