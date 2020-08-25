package org.tb.gg.global

enum Direction {
    UP, RIGHT, DOWN, LEFT

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
        }
    }
}