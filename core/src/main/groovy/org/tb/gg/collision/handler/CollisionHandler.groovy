package org.tb.gg.collision.handler

import org.tb.gg.di.definition.MultiInstanceService
import org.tb.gg.gameObject.GameObject

trait CollisionHandler<S extends GameObject, T extends GameObject> implements MultiInstanceService {
    abstract void handleCollision(S a, T b)

    boolean validForTypes(GameObject a, GameObject b) {
        return false
    }
}