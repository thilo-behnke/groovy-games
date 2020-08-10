package org.tb.gg.gameObject.component

import org.tb.gg.input.Key

enum PlayerAction {
    MOVE_UP([Key.W]), MOVE_LEFT([Key.A]), MOVE_DOWN([Key.S]), MOVE_RIGHT([Key.D]),
    LOOK_UP([Key.UP]), LOOK_LEFT([Key.LEFT]), LOOK_DOWN([Key.DOWN]), LOOK_RIGHT([Key.RIGHT]),
    SHOOT([Key.SPACE])

    Set<Key> keys

    PlayerAction(List<Key> keys) {
        this.keys = keys
    }
}