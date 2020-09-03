package org.tb.gg.gameObject.components.body

import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.resources.ImageWrapper

class SpriteBody extends Body {

    ImageWrapper image
    @Delegate
    Shape shape

    protected SpriteBody(ImageWrapper image) {
        this.image = image
        this.shape = new Rect(Vector.zeroVector(), new Vector(x: image.getWidth(), y: image.getHeight()))
    }

    protected SpriteBody(ImageWrapper image, Shape shape) {
        this.image = image
        this.shape = shape
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        if (image) {
            renderDestination.drawImage(image.getImage(), shape.boundingRect.topLeft, shape.boundingRect.rotation, options)
        } else {
            def rect = shape.boundingRect
            renderDestination.drawRect(rect.topLeft, rect.dim, rect.rotation.toFloat(), options)
        }
    }

    @Override
    void update(Long timestamp, Long delta) {

    }
}
