package org.tb.gg.gameObject.components.body

import groovy.util.logging.Log4j
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import org.tb.gg.resources.ImageWrapper
import org.tb.gg.resources.ResourceLoader

@Log4j
class SpriteBodyFactory {

    @Inject
    ResourceLoader<? extends ImageWrapper> resourceLoader

    SpriteBody fromResource(String resourceName) {
        def image = resourceLoader.getResource(resourceName)
        if (image.isEmpty()) {
            log.warn("No resource found for name ${resourceName}")
            def shape = new Rect(Vector.zeroVector(), Vector.unitVector() * 20.0)
            return new SpriteBody(null, shape)
        }
        return new SpriteBody(image.get())

    }
}
