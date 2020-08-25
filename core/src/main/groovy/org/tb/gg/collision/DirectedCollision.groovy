package org.tb.gg.collision

import org.tb.gg.global.Direction

class DirectedCollision extends Collision {
    @Delegate
    Collision collision

    Direction directionA
    Direction directionB
}
