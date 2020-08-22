package org.tb.gg.collision

import org.tb.gg.collision.handler.GameObjectCollisionHandler
import org.tb.gg.gameObject.component.enemies.EnemyGameObject
import org.tb.gg.gameObject.component.guns.BulletGameObject

class PlayerBulletEnemyCollisionHandler implements GameObjectCollisionHandler<BulletGameObject, EnemyGameObject> {
    @Override
    void handleCollision(BulletGameObject a, EnemyGameObject b) {
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
