package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionType
import org.tb.gg.gameObject.GameObject
import org.tb.gg.global.Direction
import org.tb.gg.global.geom.Vector

// TODO: Write tests. There should not be a collision after the repositioning is done.
class DirectionCollisionTypeHandler implements CollisionTypeHandler {
    /**
     * Reposition the faster of both game objects in a collision so it doesn't collide anymore.
     *
     * 0. Ignore collisions of type overlap.
     * 1. Detect which game object is faster.
     * 2. Set the faster game object by using inverted velocity until there is a gap of 1px between it and the other game object.
     *
     * @param collision
     */
    @Override
    void handleCollisionByType(Collision collision) {
        if (collision.type == CollisionType.OVERLAP) {
            return
        }
        def collisionDirectionA = collision.directionA
        def collisionDirectionB = collision.directionB

        def objectWithHigherVelocity = collision.a.physicsComponent.velocity.abs().sum() >= collision.b.physicsComponent.velocity.abs().sum() ? collision.a : collision.b
        adjustPosition(objectWithHigherVelocity, objectWithHigherVelocity == collision.a ? collision.b : collision.a, objectWithHigherVelocity == collision.a ? collisionDirectionA : collisionDirectionB)
    }

    private static adjustPosition(GameObject gameObject, GameObject otherGameObject, Direction collisionDirection) {
        Vector newCenter = gameObject.body.center
        if (collisionDirection == Direction.RIGHT) {
            // TODO: Complicated repositioning, instead implement repositioning by bounding box.
            newCenter = new Vector(x: otherGameObject.body.shape.boundingRect.topLeft.x - gameObject.body.boundingRect.dim.x / 2 - 1.0, y: gameObject.body.center.y)
        } else if (collisionDirection == Direction.LEFT) {
            newCenter = new Vector(x: otherGameObject.body.shape.boundingRect.topRight.x + gameObject.body.boundingRect.dim.x / 2 + 1.0, y: gameObject.body.center.y)
        } else if (collisionDirection == Direction.UP) {
            newCenter = new Vector(x: gameObject.body.center.x, y: otherGameObject.body.shape.boundingRect.bottomLeft.y - gameObject.body.boundingRect.dim.y / 2 - 1.0)
        } else if (collisionDirection == Direction.DOWN) {
            newCenter = new Vector(x: gameObject.body.center.x, y: otherGameObject.body.shape.boundingRect.topLeft.y + gameObject.body.boundingRect.dim.y / 2 + 1.0)
        }
        gameObject.body.setCenter(newCenter)
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
