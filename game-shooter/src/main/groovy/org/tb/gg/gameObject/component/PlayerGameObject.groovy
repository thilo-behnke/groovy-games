package org.tb.gg.gameObject.component

import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.KeyBoundGameObjectBuilder
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants

class PlayerGameObject extends GameObject {
    static create() {
        return new KeyBoundGameObjectBuilder(PlayerGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 100, y: 100), new Vector(x: 20, y: 30))))
                .setInputComponentClass(PlayerInputComponent)
                .setRenderComponent(new PlayerRenderComponent())
                .setPhysicsComponent(new PlayerPhysicsComponent())
                .setActions(PlayerAction.values().collect { it.toString() }.toSet())
                .setDefaultKeyMapping(PlayerAction.values().collectEntries { it.keys.collectEntries { key -> [(key): it.toString()] } })
                .build()
    }

    @Override
    void update(Long timestamp, Long delta) {
        super.update(timestamp, delta)

        def activeActions = inputComponent.getActiveActions().collect { PlayerAction.valueOf(it) }
        if (activeActions.isEmpty()) {
            return
        }
        def shape = body.getStructure()
        def center = shape.center
        def newX = center.x
        def newY = center.y
        // Update X.
        if (activeActions.contains(PlayerAction.RIGHT)) {
            newX = newX + 1 * delta
        } else if (activeActions.contains(PlayerAction.LEFT)) {
            newX = newX - 1 * delta
        }
        // Update Y.
        if (activeActions.contains(PlayerAction.UP)) {
            newY = newY + 1 * delta
        } else if (activeActions.contains(PlayerAction.DOWN)) {
            newY = newY - 1 * delta
        }
        shape.center = new Vector(x: newX, y: newY)

        // Collision detection won't work for rects atm if they are rotated!
        if ((activeActions.contains(PlayerAction.ROTATE_COUNTER) || activeActions.contains(PlayerAction.ROTATE)) && shape instanceof Line) {
            def line = (Line) shape
            def lineDirection = line.end - line.start
            def rotationDirection = activeActions.contains(PlayerAction.ROTATE) ? -1 : 1
            def rotatedLineDirection = lineDirection.rotate(rotationDirection * MathConstants.PI / 100)
            line.end = line.start + rotatedLineDirection
        }
    }
}
