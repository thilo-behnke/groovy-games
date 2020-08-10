package org.tb.gg.gameObject.component

import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.KeyBoundGameObjectBuilder
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.CircleOperations
import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants

class PlayerGameObject extends GameObject {
    Vector orientation

    static PlayerGameObject create() {
        def player = (PlayerGameObject) new KeyBoundGameObjectBuilder(PlayerGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 100, y: 100), new Vector(x: 20, y: 30))))
                .setInputComponentClass(PlayerInputComponent)
                .setRenderComponent(new PlayerRenderComponent())
                .setPhysicsComponent(new PlayerPhysicsComponent())
                .setActions(PlayerAction.values().collect { it.toString() }.toSet())
                .setDefaultKeyMapping(PlayerAction.values().collectEntries { it.keys.collectEntries { key -> [(key): it.toString()] } })
                .build()
        player.setOrientation(new Vector(x: 0, y: 50.0))
        return player
    }

    @Override
    void update(Long timestamp, Long delta) {
        super.update(timestamp, delta)

        def activeActions = inputComponent.getActiveActions().collect { PlayerAction.valueOf(it) }
        if (activeActions.isEmpty()) {
            return
        }

        updateMovement(activeActions, timestamp, delta)
        updateOrientation(activeActions, timestamp, delta)
    }

    private updateMovement(List<PlayerAction> activeActions, Long timestamp, Long delta) {
        def shape = body.getStructure()
        def center = shape.center
        def newX = center.x
        def newY = center.y
        // Update X.
        if (activeActions.contains(PlayerAction.MOVE_RIGHT)) {
            newX = newX + 1 * delta
        } else if (activeActions.contains(PlayerAction.MOVE_LEFT)) {
            newX = newX - 1 * delta
        }
        // Update Y.
        if (activeActions.contains(PlayerAction.MOVE_UP)) {
            newY = newY + 1 * delta
        } else if (activeActions.contains(PlayerAction.MOVE_DOWN)) {
            newY = newY - 1 * delta
        }
        shape.center = new Vector(x: newX, y: newY)
    }

    private updateOrientation(List<PlayerAction> activeActions, Long timestamp, Long delta) {
        // TODO: Update orientation
        def goal = null

        if (activeActions.containsAll([PlayerAction.LOOK_UP, PlayerAction.LOOK_RIGHT])) {
            goal = new Vector(x: 1, y: 1)
        } else if (activeActions.containsAll([PlayerAction.LOOK_RIGHT, PlayerAction.LOOK_DOWN])) {
            goal = new Vector(x: 1, y: -1)
        } else if (activeActions.containsAll([PlayerAction.LOOK_DOWN, PlayerAction.LOOK_LEFT])) {
            goal = new Vector(x: -1, y: -1)
        } else if (activeActions.containsAll([PlayerAction.LOOK_LEFT, PlayerAction.LOOK_UP])) {
            goal = new Vector(x: -1, y: 1)
        } else if (activeActions.contains(PlayerAction.LOOK_UP)) {
            goal = new Vector(x: 0, y: 1)
        } else if (activeActions.contains(PlayerAction.LOOK_RIGHT)) {
            goal = new Vector(x: 1, y: 0)
        } else if (activeActions.contains(PlayerAction.LOOK_DOWN)) {
            goal = new Vector(x: 0, y: -1)
        } else if (activeActions.contains(PlayerAction.LOOK_LEFT)) {
            goal = new Vector(x: -1, y: 0)
        }

        if (goal == null) {
            return
        }

        def angle1 = goal.angleBetween(orientation)
        def angle2 = orientation.angleBetween(goal)

        def shorterDirectionToGoal = angle1 >= angle2 ? orientation - goal : goal - orientation
        def rotationDirection = shorterDirectionToGoal.dot(orientation.rotate(MathConstants.HALF_PI)) >= 0 ? 1 : -1

        orientation = orientation.rotate(1000.0 / 60.0 * rotationDirection)

        return
    }
}
