package org.tb.gg.gameObject.component

import org.tb.gg.input.Key

enum MovableGameObjectAction {
    UP([Key.W]), LEFT([Key.A]), DOWN([Key.S]), RIGHT([Key.D]), ROTATE([Key.CTRL]), ROTATE_COUNTER([Key.SPACE])

    Set<Key> keys

    MovableGameObjectAction(List<Key> keys) {
        this.keys = keys
    }
}