package org.tb.gg.gameObject.component

import org.tb.gg.input.Key

enum MovableCircleAction {
    UP([Key.W, Key.UP]), LEFT([Key.A, Key.LEFT]), DOWN([Key.S, Key.DOWN]), RIGHT([Key.D, Key.RIGHT])

    Set<Key> keys

    MovableCircleAction(List<Key> keys) {
        this.keys = keys
    }
}