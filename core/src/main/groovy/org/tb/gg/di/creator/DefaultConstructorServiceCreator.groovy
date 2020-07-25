package org.tb.gg.di.creator

import org.tb.gg.di.definition.Service

class DefaultConstructorServiceCreator implements ServiceCreator {
    @Override
    List<Service> createServices(Set<Class<? extends Service>> serviceClasses) {
        serviceClasses.collect{
            it.getConstructor().newInstance()
        }
    }
}
