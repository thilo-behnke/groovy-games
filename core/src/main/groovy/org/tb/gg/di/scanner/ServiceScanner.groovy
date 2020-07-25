package org.tb.gg.di.scanner

import org.tb.gg.di.Service
import org.tb.gg.di.provider.ClassDefinition

interface ServiceScanner {
    Set<Class<? extends Service>> scanForServices(Set<ClassDefinition> classDefinitions)
}
