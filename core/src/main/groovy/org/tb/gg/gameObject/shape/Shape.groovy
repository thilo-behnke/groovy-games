package org.tb.gg.gameObject.shape

import org.tb.gg.collision.ShapeCollisionDetector
import org.tb.gg.di.Inject
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.Renderable

abstract class Shape implements Renderable {
    @Inject
    private ShapeCollisionDetector shapeCollisionDetector

    abstract Vector getCenter()
    abstract void setCenter(Vector pos)
    abstract boolean isPointWithin(Vector pos)

    boolean collidesWith(Shape shape) {
        return shapeCollisionDetector.detectCollision(this, shape)
    }
}