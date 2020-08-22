package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.BaseGameObject

interface CollisionDetector extends Singleton {
    Set<Collision> detect(Set<BaseGameObject> gameObjects)
}