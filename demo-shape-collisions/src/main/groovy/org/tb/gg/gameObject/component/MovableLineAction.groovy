package org.tb.gg.gameObject.component

import org.tb.gg.input.Key

enum MovableLineAction {
    UP([Key.UP]), LEFT([Key.LEFT]), DOWN([Key.DOWN]), RIGHT([Key.RIGHT])

    Set<Key> keys

    MovableLineAction(List<Key> keys) {
        this.keys = keys
    }
}