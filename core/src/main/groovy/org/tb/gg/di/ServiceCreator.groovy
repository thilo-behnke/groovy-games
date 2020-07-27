package org.tb.gg.di

import org.tb.gg.di.definition.Service

interface ServiceCreator {
    List<Service> createServices(Set<Class<? extends Service>> serviceClasses)
}