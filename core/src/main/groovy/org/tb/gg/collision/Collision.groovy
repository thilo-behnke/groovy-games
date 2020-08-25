package org.tb.gg.collision

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.global.Direction

@EqualsAndHashCode
@ToString
class Collision<S extends GameObject, T extends GameObject> {
    S a
    T b
    CollisionType type

    Direction directionA
    Direction directionB

    GameObject getAt(int index) {
        if (index == 0) {
            return a
        } else if (index == 1) {
            return b
        }
        return null
    }
}
