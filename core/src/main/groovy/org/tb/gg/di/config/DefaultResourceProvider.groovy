package org.tb.gg.di.config

import javax.annotation.Nullable

class DefaultResourceProvider implements ResourceProvider {
    @Override
    File getResourceFile(String fileName) {
        def resource = getClass().getClassLoader().getResource(fileName)
        if (!resource) {
            return null
        }

        new File(
                resource.getFile()
        );
    }
}
