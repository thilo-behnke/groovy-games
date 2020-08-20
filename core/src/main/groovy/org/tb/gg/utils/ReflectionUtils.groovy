package org.tb.gg.utils

import java.lang.reflect.Modifier

class ReflectionUtils {
    static isNotInterfaceOrAbstract(Class<?> clazz) {
        !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())
    }
}
