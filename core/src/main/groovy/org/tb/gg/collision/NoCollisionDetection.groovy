package org.tb.gg.collision

import org.tb.gg.collision.Collidable
import org.tb.gg.global.geom.Vector

trait NoCollisionDetection implements Collidable {
    @Override
    boolean collidesWith(Object structure) {
        return false
    }
}