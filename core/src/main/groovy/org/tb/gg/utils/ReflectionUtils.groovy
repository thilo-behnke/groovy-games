package org.tb.gg.utils

import java.lang.reflect.Modifier

class ReflectionUtils {
    static isNotInterfaceOrAbstract(Class<?> clazz) {
        !clazz.isInterface() && !isAbstractClass(clazz)
    }

    static isAbstractClass(Class<?> clazz) {
        Modifier.isAbstract(clazz.getModifiers())
    }

    static Set<Class<?>> getAllInterfaces(Class<?> clazz, boolean includeAbstractClasses = false) {
        Class<?> superClass = clazz.getSuperclass()
        Set<Class<?>> implementedInterfaces = includeAbstractClasses && isAbstractClass(superClass) ? clazz.getInterfaces() + (Set<Class<?>>) [superClass] : clazz.getInterfaces()

        if (superClass == Object) {
            return implementedInterfaces
        }

        Set<Class<?>> implementedInterfacesOfSuperClass = getAllInterfaces(superClass)
        return implementedInterfaces + implementedInterfacesOfSuperClass
    }
}
