package org.tb.gg.di.scanner

import com.google.common.reflect.ClassPath
import org.tb.gg.di.definition.Service
import org.tb.gg.di.definition.Singleton

class ClasspathSingletonScanner extends ClasspathServiceScanner {
    @Override
    Set<Class<? extends Service>> scanForServices() {
        def singletons = super.scanForServices(Singleton)
        removeConcreteServiceImplementations(singletons)
    }

    private static Set<Class<? extends Service>> removeConcreteServiceImplementations(Set<Class<? extends Service>> services) {
        services.findAll { !services.find { service -> it != service && service.isAssignableFrom(it) } }
    }
}
