package org.tb.gg.gameObject.component

import org.tb.gg.input.Key

enum PlayerAction {
    UP([Key.UP]), LEFT([Key.LEFT]), DOWN([Key.DOWN]), RIGHT([Key.RIGHT]), ROTATE([Key.CTRL]), ROTATE_COUNTER([Key.SPACE])

    Set<Key> keys

    PlayerAction(List<Key> keys) {
        this.keys = keys
    }
}