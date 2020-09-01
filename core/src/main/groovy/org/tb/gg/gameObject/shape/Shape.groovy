package org.tb.gg.gameObject.shape

import org.tb.gg.collision.ShapeCollisionDetector
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.Renderable

abstract class Shape implements Renderable {
    @Inject
    protected ShapeCollisionDetector shapeCollisionDetector

    abstract Shape copy()

    abstract Rect getBoundingRect()
    abstract Vector getCenter()
    abstract void setCenter(Vector pos)
    abstract boolean isPointWithin(Vector pos)
    abstract void rotate(BigDecimal radians)

    boolean collidesWith(Shape shape) {
        return shapeCollisionDetector.detect(this, shape)
    }

    boolean collidesWith(ShapeBody body) {
        return shapeCollisionDetector.detect(this, body.shape)
    }
}