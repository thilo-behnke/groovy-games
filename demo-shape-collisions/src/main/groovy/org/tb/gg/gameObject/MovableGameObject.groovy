package org.tb.gg.gameObject

import org.tb.gg.collision.CollisionRegistry
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.component.MovableCircleAction
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.InteractiveShape
import org.tb.gg.global.geom.Vector

class MovableGameObject extends GameObject {

    @Override
    void update(Long timestamp, Long delta) {
        handleMovement(timestamp, delta)
    }

    private handleMovement(Long timestamp, Long delta) {
        def activeActions = inputComponent.getActiveActions().collect { MovableCircleAction.valueOf(it) }
        def shape = physicsComponent.getStructure()
        def center = shape.center
        def newX = center.x
        def newY = center.y
        // Update X.
        if (activeActions.contains(MovableCircleAction.RIGHT)) {
            newX = newX + 1 * delta
        } else if (activeActions.contains(MovableCircleAction.LEFT)) {
            newX = newX - 1 * delta
        }
        // Update Y.
        if (activeActions.contains(MovableCircleAction.UP)) {
            newY = newY + 1 * delta
        } else if (activeActions.contains(MovableCircleAction.DOWN)) {
            newY = newY - 1 * delta
        }
        shape.center = new Vector(x: newX, y: newY)
    }
}
