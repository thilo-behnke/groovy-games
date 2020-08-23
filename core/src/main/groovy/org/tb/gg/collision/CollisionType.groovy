package org.tb.gg.collision

enum CollisionType {
    SOLID, OVERLAP

    static CollisionType getHighestPrecedence(CollisionType... collisionTypes) {
        collisionTypes.any { it == SOLID } ? SOLID : OVERLAP
    }
}