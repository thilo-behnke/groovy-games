package org.tb.gg.gameObject.components.physics


import org.tb.gg.gameObject.GameObject

abstract class PhysicsComponent<T> {
    GameObject parent

    Boolean collides

    abstract boolean shouldCollide()
}