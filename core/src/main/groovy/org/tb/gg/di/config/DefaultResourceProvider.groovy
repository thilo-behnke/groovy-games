package org.tb.gg.di.config

import org.apache.commons.io.FileUtils

class DefaultResourceProvider implements ResourceProvider {
    @Override
    File getResourceFile(String fileName) {
        def resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)
        if (!resource) {
            return null
        }
        def file = new File(fileName)
        FileUtils.copyInputStreamToFile(resource, file);
        return file
    }
}
