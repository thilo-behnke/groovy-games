package org.tb.gg.global

enum Direction {
    UP, RIGHT, DOWN, LEFT, UNDEFINED

    Direction invert() {
        switch (this) {
            case UP:
                return DOWN
            case DOWN:
                return UP
            case LEFT:
                return RIGHT
            case RIGHT:
                return LEFT
            case UNDEFINED:
                return UNDEFINED
        }
    }
}