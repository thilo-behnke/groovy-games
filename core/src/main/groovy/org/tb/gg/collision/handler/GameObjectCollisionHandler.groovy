package org.tb.gg.collision.handler


import org.tb.gg.di.definition.MultiInstanceService
import org.tb.gg.gameObject.GameObject

interface GameObjectCollisionHandler<S extends GameObject, T extends GameObject> extends CollisionHandler<S, T>, MultiInstanceService {
    void handleCollision(S a, T b)
}