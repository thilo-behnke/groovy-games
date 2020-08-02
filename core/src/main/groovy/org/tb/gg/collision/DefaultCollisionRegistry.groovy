package org.tb.gg.collision

import groovy.util.logging.Log4j
import org.tb.gg.di.Inject
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.GameObjectProvider

@Log4j
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
        log.info(collisions)
    }

    @Override
    Set<Collision> getCollisions() {
        return collisions
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
