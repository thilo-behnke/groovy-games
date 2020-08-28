package org.tb.gg.collision.strategy

import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.GameObject

interface CollisionCheckSelectionStrategy extends Singleton {
   List<List<GameObject>> selectPotentialCollisions(Set<GameObject> gameObjects)
}
