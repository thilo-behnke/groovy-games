package org.tb.gg.collision

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.utils.CollectionUtils

class DefaultCollisionHandler implements CollisionHandler {
    @Override
    Set<Collision> detect(Set<BaseGameObject> gameObjects) {
        if (gameObjects.size() < 2) {
            return []
        }
        def combinations = CollectionUtils.permutations(gameObjects, 2)

        combinations
                .findAll { BaseGameObject a, BaseGameObject b ->
                    return !a.physicsComponent?.collidesWithGroups?.intersect(b.physicsComponent?.collidesWithGroups)?.isEmpty()
                }
                .collect { BaseGameObject a, BaseGameObject b ->
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
