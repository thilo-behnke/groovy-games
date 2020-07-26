package org.tb.gg.di;

import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
    private static final Map<String, Object> serviceMap = new HashMap<>();

    public static Object getService(String serviceName) {
        // TODO: Handle failure.
        return serviceMap.get(serviceName);
    }

    public static void setService(Object service) {
        setService(service, service.getClass().getName());
    }

    public static void setService(Object service, String name) {
        if(serviceMap.containsKey(name)) {
            return;
        }
        serviceMap.put(name, service);
    }

    public static void reset() {
        // TODO: What to do with services in the Map? Type is unclear here to call destroy on each...
        serviceMap.clear();
    }

}
