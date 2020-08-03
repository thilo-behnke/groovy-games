package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.GameObject

interface CollisionRegistry extends Singleton, Updateable {
    Set<Collision> getCollisions()
    boolean hasCollision(GameObject gameObject)
}