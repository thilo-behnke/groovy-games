package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.GameObject

interface CollisionCoordinator extends Singleton, Updateable {
    Set<Collision> getCollisions()
    Set<Collision> getCollisions(GameObject gameObject)
}