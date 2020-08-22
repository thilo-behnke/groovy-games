package org.tb.gg.collision

import org.tb.gg.di.definition.MultiInstanceService
import org.tb.gg.gameObject.GameObject

trait CollisionHandler<S extends GameObject, T extends GameObject> implements MultiInstanceService {

    void handleCollision(GameObject a, GameObject b) {
        if (a instanceof S && b instanceof T) {
            handleCollisionImplementation(a, b)
        } else if (a instanceof T && b instanceof S) {
            handleCollisionImplementation(b, a)
        } else {
            throw new IllegalArgumentException("Collision handler does not handle collision between ${a.class} and ${b.class}.")
        }
    }

    abstract void handleCollisionImplementation(S a, T b)

    boolean validForTypes(GameObject a, GameObject b) {
        a instanceof S && b instanceof T || a instanceof T && b instanceof S
    }
}