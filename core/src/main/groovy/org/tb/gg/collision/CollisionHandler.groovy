package org.tb.gg.collision

import org.tb.gg.di.definition.MultiInstanceService
import org.tb.gg.gameObject.GameObject

// TODO: Use AST to create method that extracts type from generics.
interface CollisionHandler<S extends GameObject, T extends GameObject> extends MultiInstanceService {
    abstract void handleCollision(S a, T b)
}