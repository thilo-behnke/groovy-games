package org.tb.gg.collision

import groovy.util.logging.Log4j
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.utils.CollectionUtils

@Log4j
class DefaultCollisionHandler implements CollisionHandler {
    @Override
    Set<Collision> detect(Set<BaseGameObject> gameObjects) {
        if (gameObjects.size() < 2) {
            return []
        }
        def combinations = CollectionUtils.permutations(gameObjects, 2)

        combinations
                .findAll { BaseGameObject a, BaseGameObject b ->
                    if (!a.body || !b.body) {
                        return false
                    }
                    return a.physicsComponent.collidesWith(b.physicsComponent)
                }
                .collect { BaseGameObject a, BaseGameObject b ->
                    def areColliding = a.body.collidesWith(b.body.getStructure())
                    if (!areColliding) {
                        return null
                    }

                    def collision = new Collision(a: a, b: b)
                    log.debug("Collision detected: ${collision}".toString())
                    return collision
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
