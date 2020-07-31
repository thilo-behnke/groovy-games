package org.tb.gg.di;

import java.util.*;

public class DependencyRegistry {
    private static final Map<String, Set<String>> dependencyRegistry = new HashMap<>();

    static void registerDependency(Class<?> clazz, Class<?> dependency) {
        if(clazz.equals(dependency)) {
            throw new IllegalArgumentException("Can't declare a dependency from the given class to itself!");
        }
        Set<String> dependencies = dependencyRegistry.computeIfAbsent(clazz.getName(), k -> new HashSet<>());
        dependencies.add(dependency.getName());
    }

    public static Set<String> getDependencies(Class<?> clazz) {
        return dependencyRegistry.getOrDefault(clazz.getName(), new HashSet<>());
    }
}
