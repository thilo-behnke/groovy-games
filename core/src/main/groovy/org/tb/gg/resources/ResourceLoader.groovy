package org.tb.gg.resources

interface ResourceLoader<I> {
    void loadResource(String path, String name)
    Optional<I> getResource(String name)
}