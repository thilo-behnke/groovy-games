package org.tb.gg.collision.strategy

import org.tb.gg.gameObject.GameObject
import org.tb.gg.utils.CollectionUtils

/**
 * Use euclidean distance between the objects to decide if there collision is possible at all.
 */
class DistanceHeuristicCollisionCheckSelectionStrategy implements CollisionCheckSelectionStrategy {
    @Override
    List<List> selectPotentialCollisions(Set<GameObject> gameObjects) {
        if (gameObjects.isEmpty()) {
            return []
        }
        def allCombinations = CollectionUtils.permutations(gameObjects, 2)
        def combinationsForDistanceHeuristic = allCombinations.findAll {
            def gameObjectA = it[0]
            def gameObjectB = it[1]
            if (!gameObjectA.body || !gameObjectB.body) {
                return false
            }
            def distanceCenterToCenter = gameObjectA.body.center.distance(gameObjectB.body.center)
            def collisionThreshold = gameObjectA.body.boundingRect.diagonalLength() + gameObjectB.body.boundingRect.diagonalLength()

            return distanceCenterToCenter <= collisionThreshold
        }

        return combinationsForDistanceHeuristic
    }
}
