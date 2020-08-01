package org.tb.gg.di.config

import org.apache.tools.ant.types.Resource

import javax.annotation.Nullable

interface ResourceProvider {
    @Nullable
    File getResourceFile(String fileName)
}