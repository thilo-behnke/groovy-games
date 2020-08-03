package org.tb.gg.gameObject.components.physics

import org.tb.gg.collision.Collidable
import org.tb.gg.gameObject.shape.Shape

trait CollidableBody<T> implements Collidable<T> {
    protected Shape shape

    CollidableBody(Shape shape) {
        this.shape = shape
    }

}