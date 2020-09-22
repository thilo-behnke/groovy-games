package org.tb.gg.di.config

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

import java.nio.charset.StandardCharsets
import java.nio.file.Paths

class DefaultResourceProvider implements ResourceProvider {
    @Override
    String getResourceFileContent(String fileName) {
        def resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)
        if (!resource) {
            return null
        }
        return IOUtils.toString(resource, StandardCharsets.UTF_8)
    }
}
