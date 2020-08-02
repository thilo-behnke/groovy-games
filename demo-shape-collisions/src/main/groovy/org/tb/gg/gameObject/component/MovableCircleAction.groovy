package org.tb.gg.gameObject.component

import org.tb.gg.input.Key

enum MovableCircleAction {
    LEFT(Key.W), UP(Key.A), DOWN(Key.S), RIGHT(Key.D)

    Key key

    MovableCircleAction(Key key) {
        this.key = key
    }
}