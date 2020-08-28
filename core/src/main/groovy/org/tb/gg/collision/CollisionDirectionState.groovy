package org.tb.gg.collision


import org.tb.gg.global.Direction

class CollisionDirectionState {
    private Map<Direction, Boolean> collisions = [
            (Direction.UP): false,
            (Direction.RIGHT): false,
            (Direction.DOWN): false,
            (Direction.LEFT): false,
    ]

    void setDirectionCollision(Direction direction) {
        if (direction == Direction.UNDEFINED) {
            return
        }
        collisions[direction] = true
    }

    boolean getCollides() {
        collisions.values().any()
    }

    void reset() {
        collisions[Direction.UP] = false
        collisions[Direction.RIGHT] = false
        collisions[Direction.DOWN] = false
        collisions[Direction.LEFT] = false
    }
}
