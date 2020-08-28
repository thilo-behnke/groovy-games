package org.tb.gg.resources

import org.tb.gg.di.definition.Singleton

interface ResourceLoader<I extends ImageWrapper> extends Singleton {
    void loadResource(String path, String name)
    Optional<I> getResource(String name)
}