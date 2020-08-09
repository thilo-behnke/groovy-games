package org.tb.gg.collision

import org.tb.gg.gameObject.GameObject
import org.tb.gg.utils.CollectionUtils

class DefaultCollisionHandler implements CollisionHandler {
    @Override
    Set<Collision> detect(Set<GameObject> gameObjects) {
        if (gameObjects.size() < 2) {
            return []
        }
        def combinations = CollectionUtils.permutations(gameObjects, 2)

        combinations
                .collect { GameObject a, GameObject b ->
                    // TODO: This should be a flag on the physics component to decide whether collisions are checked or not.
                    if(!a.physicsComponent || !b.physicsComponent) {
                        return null
                    }
                    def areColliding = a.body.collidesWith(b.body.getStructure())
                    if (!areColliding) {
                        return null
                    }
                    return new Collision(a: a, b: b)
                }
                .findAll { it }
                .collect { (Collision) it }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
