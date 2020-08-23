package org.tb.gg.collision

import groovy.util.logging.Log4j
import org.tb.gg.collision.handler.GameObjectCollisionHandler
import org.tb.gg.gameObject.component.enemies.EnemyGameObject
import org.tb.gg.gameObject.component.guns.BulletGameObject

@Log4j
class PlayerBulletEnemyCollisionHandler extends GameObjectCollisionHandler<BulletGameObject, EnemyGameObject> {
    @Override
    void handleCollision(BulletGameObject a, EnemyGameObject b) {
        log.debug("Handling bullet <> enemy collision for ${a} and ${b}".toString())
        a.shouldBeDestroyed = true
        b.hp = b.hp - a.damage
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
