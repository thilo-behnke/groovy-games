package org.tb.gg.di.config

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.tb.gg.di.definition.Service

class ServiceMappingRegistry {
    BiMap<String, Class<? extends Service>> registeredServices = HashBiMap.create()

    @Override
    Object invokeMethod(String name, Object args) {
        Object[] argArray;
        if (args instanceof Object[]) {
            argArray = (Object[]) args;
        } else {
            argArray = new Object[]{args};
        }

        if(!(argArray[0] instanceof Class)) {
            return super.invokeMethod(name, args)
        }

        def interfaceName = name.capitalize()
        registeredServices[interfaceName] = (Class) argArray[0]
        return null
    }

    Class<? extends Service> getImplementationForBaseClass(String baseClassName) {
        return registeredServices.get(baseClassName)
    }
}
