package org.tb.gg.di.provider

import com.google.common.reflect.ClassPath

class TopLevelClassDefinitionProvider implements ClassDefinitionProvider {
    final ClassLoader loader

    TopLevelClassDefinitionProvider() {
        loader = Thread.currentThread().getContextClassLoader();
    }

    @Override
    Set<ClassDefinition> getClassDefinitions() {
        ClassPath.from(loader).getTopLevelClasses().collect{new ClassDefinition(className: it.getName(), packageName: it.getPackageName())}
    }
}
