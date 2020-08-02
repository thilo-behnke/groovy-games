package org.tb.gg.gameObject.components

import org.tb.gg.collision.Collidable
import org.tb.gg.global.geom.Vector

trait NoCollisionDetection implements Collidable {
    // TODO: Ugly, only relevant for actual shapes.
    @Override
    Vector getCenter() {
        return null
    }

    @Override
    Vector getClosestPointInDirectionFromCenter(Vector direction) {
        return null
    }

    @Override
    boolean isPointWithin(Vector pos) {
        return false
    }

    @Override
    boolean doesOverlapWith(Collidable collidable) {
        return false
    }
}