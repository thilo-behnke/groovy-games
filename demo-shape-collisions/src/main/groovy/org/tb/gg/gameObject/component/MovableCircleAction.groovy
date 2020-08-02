package org.tb.gg.gameObject.component

import org.tb.gg.input.Key

enum MovableCircleAction {
    UP(Key.W), LEFT(Key.A), DOWN(Key.S), RIGHT(Key.D)

    Key key

    MovableCircleAction(Key key) {
        this.key = key
    }
}