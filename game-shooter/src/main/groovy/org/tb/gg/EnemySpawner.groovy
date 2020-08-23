package org.tb.gg

import groovy.util.logging.Log4j
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.component.enemies.EnemyGameObject
import org.tb.gg.global.geom.Vector
import org.tb.gg.spawner.Spawner
import org.tb.gg.world.WorldStateProvider

@Log4j
class EnemySpawner implements Spawner<GameObject> {
    @Inject
    WorldStateProvider worldStateProvider

    Set<GameObject> spawn() {
        if (worldStateProvider.get().currentLoopTimestamp % 38 == 0) {
            log.info("EnemySpawner triggered, spawning enemy.")
            def x = worldStateProvider.get().currentLoopTimestamp % worldStateProvider.get().bounds.topRight.x.toLong()
            def y = worldStateProvider.get().currentLoopTimestamp % worldStateProvider.get().bounds.topRight.y.toLong()
            def pos = new Vector(x: x, y: y)
            log.info("EnemySpawner triggered, spawning enemy at ${pos}.".toString())
            def enemy = EnemyGameObject.create(pos)
            enemy.setHp(100)
            enemy.setScore(50)
            return [enemy]
        }
        return []
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
