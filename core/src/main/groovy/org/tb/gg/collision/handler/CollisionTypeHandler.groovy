package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.di.definition.Singleton

interface CollisionTypeHandler extends Singleton {
    void handleCollisionByType(Collision collision)
}