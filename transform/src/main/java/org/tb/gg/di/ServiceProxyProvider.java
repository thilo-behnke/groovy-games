package org.tb.gg.di;

import java.util.HashMap;
import java.util.Map;

public class ServiceProxyProvider {
    private static final Map<String, ServiceProxy> proxyMap = new HashMap<>();

    public static Object getService(String serviceName) {
        if(proxyMap.containsKey(serviceName)) {
            return proxyMap.get(serviceName);
        }
        ServiceProxy serviceProxy = new ServiceProxy();
        proxyMap.put(serviceName, serviceProxy);
        return serviceProxy;
    }

    public static void setService(Object service) {
        if(!proxyMap.containsKey(service.getClass().getName())) {
            return;
        }
        ServiceProxy serviceProxy = proxyMap.get(service.getClass().getName());
        serviceProxy.setObject(service);
    }
}

