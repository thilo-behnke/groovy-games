package org.tb.gg.gameObject

import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.component.MovableCircleAction
import org.tb.gg.global.geom.Vector

class CircleGameObject extends GameObject {
    Vector center
    BigDecimal radius

    @Override
    void update(Long timestamp, Long delta) {
        def activeActions = inputComponent.getActiveActions().collect { MovableCircleAction.valueOf(it) }
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
        center = new Vector(x: newX, y: newY)
    }
}
