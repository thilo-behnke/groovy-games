package org.tb.gg.collision


import org.tb.gg.di.Inject
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.GameObjectProvider

class DefaultCollisionRegistry implements CollisionRegistry {
    @Inject
    private CollisionHandler collisionDetector
    @Inject
    private GameObjectProvider gameObjectProvider

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
        }
    }

    @Override
    Set<Collision> getCollisions() {
        return collisions
    }

    @Override
    boolean hasCollision(BaseGameObject gameObject) {
        // TODO: Find special collection type for this.
        return collisions.find {
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
