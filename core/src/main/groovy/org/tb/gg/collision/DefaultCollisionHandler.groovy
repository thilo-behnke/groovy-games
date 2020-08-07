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
                    if(!a.physicsComponent || !b.physicsComponent) {
                        return null
                    }
                    return a.physicsComponent.getStructure().collidesWith(b.physicsComponent.getStructure())
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
