package org.tb.gg.gameObject.component.player

import org.tb.gg.input.Key

enum PlayerAction {
    MOVE_UP([Key.W]), MOVE_LEFT([Key.A]), MOVE_DOWN([Key.S]), MOVE_RIGHT([Key.D]),
    THRUST([Key.UP]), LOOK_LEFT([Key.LEFT]), LOOK_DOWN([Key.DOWN]), LOOK_RIGHT([Key.RIGHT]),
    SHOOT([Key.SPACE]), SWITCH_WEAPON([Key.CTRL])

    Set<Key> keys

    PlayerAction(List<Key> keys) {
        this.keys = keys
    }
}