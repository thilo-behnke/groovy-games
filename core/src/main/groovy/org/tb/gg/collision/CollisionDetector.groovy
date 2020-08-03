package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.GameObject

interface CollisionDetector extends Singleton {
    Set<Collision> detect(Set<GameObject> gameObjects)
}