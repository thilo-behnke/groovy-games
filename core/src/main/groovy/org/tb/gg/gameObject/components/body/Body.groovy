package org.tb.gg.gameObject.components.body


import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.Renderable

abstract class Body implements Updateable, Renderable {
    // TODO: static, dynamic, velocity, etc.

    abstract Shape getShape()
    abstract Rect getBoundingRect()
    abstract Vector getCenter()

    boolean collidesWith(Body body) {
        return this.shape.collidesWith(body.shape)
    }

    boolean collidesWith(Shape shape) {
        return this.shape.collidesWith(shape)
    }
}
