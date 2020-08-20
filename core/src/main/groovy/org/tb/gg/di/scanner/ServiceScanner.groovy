package org.tb.gg.di.scanner

import org.tb.gg.di.definition.Service

interface ServiceScanner {
    Set<Class<? extends Service>> scanForServices()
}
