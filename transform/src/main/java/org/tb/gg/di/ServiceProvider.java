package org.tb.gg.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceProvider {
    private static final Map<String, Object> singletonServiceMap = new HashMap<>();
    private static final Map<String, List<Object>> multiInstanceServiceMap = new HashMap<>();

    public static Map<String, Object> getServices() {
        return singletonServiceMap;
    }

    public static Object getSingletonService(String serviceName) {
        // TODO: Handle failure.
        return singletonServiceMap.get(serviceName);
    }

    public static List<Object> getMultiInstanceServices(String serviceName) {
        return multiInstanceServiceMap.get(serviceName);
    }

    public static void registerSingletonService(Object service) {
        registerSingletonService(service, service.getClass().getSimpleName());
    }

    public static void registerSingletonService(Object service, String name) {
        if (singletonServiceMap.containsKey(name)) {
            return;
        }
        singletonServiceMap.put(name, service);
    }

    public static void registerMultiInstanceService(Object service, String name) {
        List<Object> existingInstances = multiInstanceServiceMap.computeIfAbsent(name, k -> new ArrayList<>());
        existingInstances.add(service);
    }

    public static void reset() {
        // TODO: What to do with services in the Map? Type is unclear here to call destroy on each...
        singletonServiceMap.clear();
    }

}
