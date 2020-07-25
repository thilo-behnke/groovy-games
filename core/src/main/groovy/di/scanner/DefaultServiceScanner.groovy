package di.scanner

import di.Service
import org.reflections.Reflections

class DefaultServiceScanner implements ServiceScanner {
    @Override
    Set<Class<? extends Service>> scanForServices(String pkg = '') {
        Reflections reflections = new Reflections(pkg)
        def services = reflections.getSubTypesOf(Service.class)
        return services
    }
}
