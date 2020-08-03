package org.tb.gg.gameObject.components.physics

import org.tb.gg.collision.Collidable
import org.tb.gg.gameObject.GameObject

abstract class PhysicsComponent<T> implements Collidable<T> {
    GameObject parent

    abstract Body getBody()
    abstract T getStructure()
}