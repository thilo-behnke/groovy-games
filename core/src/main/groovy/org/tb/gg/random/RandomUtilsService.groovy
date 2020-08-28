package org.tb.gg.random

import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.GameObject
import org.tb.gg.global.geom.Vector
import org.tb.gg.world.WorldStateProvider

class RandomUtilsService implements Singleton {

    @Inject
    WorldStateProvider worldStateProvider

    // TODO: Very rudimentary implementation - improve.
    Vector getRandomPositionInWorldBounds(GameObject gameObject, long seed = 0) {
        def x = worldStateProvider.get().currentLoopTimestamp % (worldStateProvider.get().bounds.topRight.x.toLong() + seed)
        x = Math.min(x, worldStateProvider.get().bounds.topRight.x.toLong() - gameObject.body.boundingRect.dim.x.toLong())
        def y = worldStateProvider.get().currentLoopTimestamp % (worldStateProvider.get().bounds.topRight.y.toLong() + seed)
        y = Math.min(y, worldStateProvider.get().bounds.topRight.y.toLong() - gameObject.body.boundingRect.dim.y.toLong())
        new Vector(x: x, y: y)
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
