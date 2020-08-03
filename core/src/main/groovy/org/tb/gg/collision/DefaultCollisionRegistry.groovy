package org.tb.gg.collision

import groovy.util.logging.Log4j
import org.tb.gg.di.Inject
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider

class DefaultCollisionRegistry implements CollisionRegistry {
    @Inject
    private CollisionDetector collisionDetector
    @Inject
    private GameObjectProvider gameObjectProvider

    private Set<Collision> collisions

    @Override
    void update(Long timestamp, Long delta) {
        // TODO: Don't do this every tick
        collisions = collisionDetector.detect(gameObjectProvider.getGameObjects())
        collisions.each { GameObject a, GameObject b ->
            a.physicsComponent.collides = true
            b.physicsComponent.collides = true
        }
    }

    @Override
    Set<Collision> getCollisions() {
        return collisions
    }

    @Override
    boolean hasCollision(GameObject gameObject) {
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
