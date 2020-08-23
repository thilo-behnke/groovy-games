package org.tb.gg.collision

import com.google.common.collect.ImmutableMap
import groovy.transform.Immutable
import org.tb.gg.global.Direction

@Immutable
class CollisionDirectionRegistry {
    private Map<Direction, Boolean> collisions = [
            (Direction.UP): false,
            (Direction.RIGHT): false,
            (Direction.DOWN): false,
            (Direction.LEFT): false,
    ]

    void setDirectionCollision(Direction direction, boolean collides) {
        collisions[direction] = collides
    }

    void reset() {
        setDirectionCollision(Direction.UP, false)
        setDirectionCollision(Direction.RIGHT, false)
        setDirectionCollision(Direction.DOWN, false)
        setDirectionCollision(Direction.LEFT, false)
    }
}
