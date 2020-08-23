package org.tb.gg.collision

import org.tb.gg.collision.handler.CollisionHandlerReferrer
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider

class DefaultCollisionRegistry implements CollisionRegistry {
    @Inject
    private CollisionDetector collisionDetector
    @Inject
    private GameObjectProvider gameObjectProvider
    @Inject
    private CollisionHandlerReferrer collisionHandlerReferrer

    private Set<Collision> collisions

    @Override
    void update(Long timestamp, Long delta) {
        // TODO: Don't do this every tick
        gameObjectProvider.getGameObjects().each {
            it.physicsComponent?.collides = false
        }
        collisions = collisionDetector.detect(gameObjectProvider.getGameObjects())
        collisions.each { collision ->
            collision.a.physicsComponent.collides = true
            collision.b.physicsComponent.collides = true

            collisionHandlerReferrer.handleCollision(collision)
        }
    }

    @Override
    Set<Collision> getCollisions() {
        return collisions
    }

    @Override
    Set<Collision> getCollisions(GameObject gameObject) {
        // TODO: Find special collection type for this.
        return collisions.findAll {
            it.a == gameObject || it.b == gameObject
        }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
