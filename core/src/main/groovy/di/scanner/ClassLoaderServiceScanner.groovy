package di.scanner

import com.google.common.reflect.ClassPath
import di.Service

import java.util.stream.Collectors

class ClassLoaderServiceScanner implements ServiceScanner {
    private ClassLoader classLoader

    ClassLoaderServiceScanner(ClassLoader classLoader) {
        this.classLoader = classLoader
    }

    @Override
    Set<Class<? extends Service>> scanForServices() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        ClassPath.from(loader).getTopLevelClasses().stream()
                .map {
                    Class.forName(it.getName())
                }
                .filter {
                    Service.isAssignableFrom(it)
                }
                .map {
                    (Class<? extends Service>) it
                }
                .collect(Collectors.toList())
    }
}
