package org.tb.gg.di

import org.tb.gg.di.definition.Service

interface ServiceCreationOrderResolver {
    List<Class<? extends Service>> determineCreationOrder(Set<Class<? extends Service>> serviceClasses)
}
