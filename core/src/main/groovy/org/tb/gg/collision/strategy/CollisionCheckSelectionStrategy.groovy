package org.tb.gg.collision.strategy

import org.tb.gg.gameObject.GameObject

interface CollisionCheckSelectionStrategy {
   List<List<GameObject>> selectPotentialCollisions(Set<GameObject> gameObjects)
}
