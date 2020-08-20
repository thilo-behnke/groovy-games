package org.tb.gg

import org.tb.gg.di.Inject
import org.tb.gg.engine.SceneProvider
import org.tb.gg.gameObject.GameObject
import org.tb.gg.spawner.Spawner
import org.tb.gg.world.WorldStateProvider

class EnemySpawner implements Spawner<GameObject> {
    @Inject SceneProvider sceneProvider
    @Inject WorldStateProvider worldStateProvider

    Set<GameObject> spawn() {
        return []
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
