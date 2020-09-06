package org.tb.gg.di.config

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.tb.gg.di.ServiceProvider
import org.tb.gg.di.definition.Service

import javax.annotation.Nullable

class ServiceMappingRegistry {
    BiMap<String, Class<? extends Service>> registeredServices = HashBiMap.create()
    BiMap<String, Service> registeredServiceInstances = HashBiMap.create()

    @Override
    Object invokeMethod(String name, Object args) {
        Object[] argArray;
        if (args instanceof Object[]) {
            argArray = (Object[]) args;
        } else {
            argArray = new Object[]{args};
        }

        def interfaceName = name.capitalize()
        if (argArray[0] instanceof Class) {
            registeredServices[interfaceName] = (Class) argArray[0]
        } else {
            registeredServiceInstances[interfaceName] = (Service) argArray[0]
        }
        return null
    }

    @Nullable
    Class<? extends Service> getImplementationForBaseClass(String baseClassName) {
        return registeredServices.get(baseClassName)
    }

    @Nullable
    Service getServiceInstanceForBaseClass(String baseClassName) {
        return registeredServiceInstances.get(baseClassName)
    }
}
