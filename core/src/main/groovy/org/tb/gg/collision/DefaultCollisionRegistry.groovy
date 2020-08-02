package org.tb.gg.collision

import org.tb.gg.di.Inject
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.GameObjectProvider

class DefaultCollisionRegistry implements CollisionRegistry, Updateable {
    @Inject
    private CollisionDetector collisionDetector
    @Inject
    private GameObjectProvider gameObjectProvider

    private Set<Collision> collisions

    @Override
    void update(Long timestamp, Long delta) {
        // TODO: Don't do this every tick
        collisions = collisionDetector.detect(gameObjectProvider.getGameObjects())
    }

    @Override
    Set<Collision> getCollisions() {
        return null
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
