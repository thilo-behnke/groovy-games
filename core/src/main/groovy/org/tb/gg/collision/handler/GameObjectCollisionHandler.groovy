package org.tb.gg.collision.handler


import org.tb.gg.di.definition.MultiInstanceService
import org.tb.gg.gameObject.GameObject

abstract class GameObjectCollisionHandler<S extends GameObject, T extends GameObject> implements CollisionHandler<S, T>, MultiInstanceService {
    abstract void handleCollision(S a, T b)

    // TODO: Create with AST transformation.
    @Override
    boolean validForTypes(Object a, Object b) {
        return false
    }
}