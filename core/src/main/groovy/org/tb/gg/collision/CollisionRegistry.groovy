package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton
import org.tb.gg.engine.helper.Updateable

interface CollisionRegistry extends Singleton, Updateable {
    Set<Collision> getCollisions()
}