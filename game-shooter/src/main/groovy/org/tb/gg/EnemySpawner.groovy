package org.tb.gg

import groovy.util.logging.Log4j
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.component.enemies.EnemyGameObject
import org.tb.gg.global.geom.Vector
import org.tb.gg.random.RandomUtilsService
import org.tb.gg.spawner.Spawner
import org.tb.gg.world.WorldStateProvider

@Log4j
class EnemySpawner implements Spawner<GameObject> {
    @Inject
    WorldStateProvider worldStateProvider
    @Inject
    GameObjectProvider gameObjectProvider
    @Inject
    RandomUtilsService randomUtilsService

    private static final ENEMY_LIMIT = 20

    Set<GameObject> spawn() {
        if (shouldSpawn()) {
            return spawnEnemy()
        }
        return []
    }

    private shouldSpawn() {
        def enemyLimitReached = gameObjectProvider.getGameObjects()
                .findAll { it instanceof EnemyGameObject }.size() >= ENEMY_LIMIT
        if (enemyLimitReached) {
            return false
        }
        def timeHasCome = worldStateProvider.get().currentLoopTimestamp % 38 == 0
        return timeHasCome
    }

    private spawnEnemy() {
        log.info("EnemySpawner triggered, spawning enemy.")
        def enemy = EnemyGameObject.create(Vector.zeroVector())
        enemy.setHp(100)
        enemy.setScore(50)

        def couldSpawn = findSpawnPosition(enemy)
        if (!couldSpawn) {
            log.info("Could not find position to spawn enemy.".toString())
            return []
        }
        log.info("EnemySpawner triggered, spawning enemy at ${enemy.body.center}.".toString())

        return [enemy]
    }

    private findSpawnPosition(EnemyGameObject enemy) {
        for (int i = 0; i <= 20; i++) {
            enemy.body.center = randomUtilsService.getRandomPositionInWorldBounds(enemy)
            if (!collidesWithGameObject(enemy)) {
                return true
            }
        }
        return false
    }

    private collidesWithGameObject(EnemyGameObject enemy) {
        gameObjectProvider.getGameObjects().find { it.body.collidesWith(enemy.body) }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
