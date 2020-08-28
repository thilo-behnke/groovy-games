package org.tb.gg.collision.strategy

import org.tb.gg.gameObject.GameObject

interface CollisionCheckSelectionStrategy {
   List<List> selectPotentialCollisions(List<GameObject> gameObjects)
}
