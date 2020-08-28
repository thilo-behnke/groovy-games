package org.tb.gg.collision

import org.tb.gg.collision.handler.CollisionHandlerReferrer
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider

class DefaultCollisionCoordinator implements CollisionCoordinator {
    @Inject
    private CollisionDetector collisionDetector
    @Inject
    private GameObjectProvider gameObjectProvider
    @Inject
    private CollisionHandlerReferrer collisionHandlerReferrer
    @Inject
    private CollisionDirectionResolver collisionDirectionResolver

    private Set<Collision> collisions

    @Override
    void update(Long timestamp, Long delta) {
        gameObjectProvider.getGameObjects().each {
            if (it.physicsComponent) {
                // There are currently cases where the collision directions can't be determined, so the property collides is used as a backup.
                it.physicsComponent.collisions.reset()
                it.physicsComponent.collides = false
            }
        }
        // TODO: Don't do this every tick
        collisions = collisionDetector.detect(gameObjectProvider.getGameObjects())
        collisions.each { collision ->
            collision.a.physicsComponent.collides = true
            collision.b.physicsComponent.collides = true

            resolveCollisionDirection(collision)
            handleCollision(collision)
        }
    }

    private resolveCollisionDirection(Collision collision) {
        def collisionDirectionAtoB = collisionDirectionResolver.resolveCollisionDirection(collision)

        collision.a.physicsComponent.collisions.setDirectionCollision(collisionDirectionAtoB)
        collision.b.physicsComponent.collisions.setDirectionCollision(collisionDirectionAtoB.invert())
        // TODO: Not nice that this is set after creation of the object, find better approach.
        collision.directionA = collisionDirectionAtoB
        collision.directionB = collisionDirectionAtoB.invert()
    }

    private handleCollision(Collision collision) {
        collisionHandlerReferrer.handleCollision(collision)
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
