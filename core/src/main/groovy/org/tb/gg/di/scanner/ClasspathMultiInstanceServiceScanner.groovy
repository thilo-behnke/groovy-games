package org.tb.gg.di.scanner

import org.tb.gg.di.definition.MultiInstanceService
import org.tb.gg.di.definition.Service
import org.tb.gg.utils.ReflectionUtils

class ClasspathMultiInstanceServiceScanner extends ClasspathServiceScanner {
    @Override
    Set<Class<? extends Service>> scanForServices() {
        removeInterfacesAndAbstractClasses(super.scanForServices(MultiInstanceService))
    }

    private static Set<Class<? extends Service>> removeInterfacesAndAbstractClasses(Set<Class<? extends Service>> services) {
        services.findAll { ReflectionUtils.isNotInterfaceOrAbstract(it) }
    }
}
