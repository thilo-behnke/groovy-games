package di.scanner

import di.Service

interface ServiceScanner {
    Set<Class<? extends Service>> scanForServices()
}
