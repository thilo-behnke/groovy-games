package org.tb.gg.di.injector

import org.tb.gg.di.definition.Service

interface ServiceInjector {
    void injectServices(List<Service> services)
}