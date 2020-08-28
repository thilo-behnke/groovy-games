package org.tb.gg.collision

import groovy.util.logging.Log4j
import org.tb.gg.collision.handler.GameObjectCollisionHandler
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.component.enemies.EnemyGameObject
import org.tb.gg.gameObject.component.guns.BulletGameObject
import org.tb.gg.score.ScoreManager

@Log4j
class PlayerBulletEnemyCollisionHandler extends GameObjectCollisionHandler<BulletGameObject, EnemyGameObject> {
    @Inject ScoreManager scoreManager

    @Override
    void handleCollision(BulletGameObject a, EnemyGameObject b) {
        log.debug("Handling bullet <> enemy collision for ${a} and ${b}".toString())
        handleDamage(a, b)
    }

    private handleDamage(BulletGameObject a, EnemyGameObject b) {
        a.shouldBeDestroyed = true
        b.wasHitRecently = true
        if (b.hp <= 0) {
            return
        }

        b.hp = b.hp - a.damage
        if (b.hp <= 0) {
            b.shouldBeDestroyed = true
            handleScore(b)
        }
    }

    private handleScore(EnemyGameObject b) {
        scoreManager + (b.score ?: 0)
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
