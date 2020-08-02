package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton

interface CollisionRegistry extends Singleton {
    Set<Collision> getCollisions()
}