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
        if(serviceMap.containsKey(service.getClass().getName())) {
            return;
        }
        serviceMap.put(service.getClass().getName(), service);
    }
}
