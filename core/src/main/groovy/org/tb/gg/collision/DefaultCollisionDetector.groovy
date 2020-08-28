package org.tb.gg.collision

import groovy.util.logging.Log4j
import org.tb.gg.collision.strategy.CollisionCheckSelectionStrategy
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.utils.CollectionUtils

@Log4j
class DefaultCollisionDetector implements CollisionDetector {

    @Inject CollisionCheckSelectionStrategy collisionCheckSelectionStrategy

    @Override
    Set<Collision> detect(Set<GameObject> gameObjects) {
        if (gameObjects.size() < 2) {
            return []
        }
        def combinations = collisionCheckSelectionStrategy.selectPotentialCollisions(gameObjects)

        combinations
                .findAll { BaseGameObject a, BaseGameObject b ->
                    if (!a.body || !b.body)     {
                        return false
                    }
                    return a.physicsComponent.shouldCollideWith(b.physicsComponent)
                }
                .collect { BaseGameObject a, BaseGameObject b ->
                    def areColliding = a.body.collidesWith(b.body)
                    if (!areColliding) {
                        return null
                    }

                    def collisionType = a.physicsComponent.getCollisionType(b.physicsComponent)
                    def collision = new Collision(a: a, b: b, type: collisionType)
                    log.info("Collision detected: ${collision}".toString())
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
