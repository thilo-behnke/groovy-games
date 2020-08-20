package org.tb.gg.collision

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.tb.gg.gameObject.BaseGameObject

@EqualsAndHashCode
@ToString
class Collision {
    BaseGameObject a
    BaseGameObject b

    BaseGameObject getAt(int index) {
        if (index == 0) {
            return a
        } else if (index == 1) {
            return b
        }
        return null
    }
}
