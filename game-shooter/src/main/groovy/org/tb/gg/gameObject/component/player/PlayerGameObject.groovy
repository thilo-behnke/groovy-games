package org.tb.gg.gameObject.component.player


import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.component.guns.Gun
import org.tb.gg.gameObject.component.guns.Pistol
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.KeyBoundGameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants

class PlayerGameObject extends BaseGameObject {
    private Gun gun

    static PlayerGameObject create() {
        def pos = new Vector(x: 100, y: 100)
        def player = (PlayerGameObject) new KeyBoundGameObjectBuilder(PlayerGameObject)
                .setBody(new ShapeBody(new Rect(pos, new Vector(x: 20, y: 30))))
                .setInputComponentClass(PlayerInputComponent)
                .setRenderComponent(new PlayerRenderComponent())
                .setPhysicsComponent(PlayerPhysicsComponent.create())
                .setActions(PlayerAction.values().collect { it.toString() }.toSet())
                .setDefaultKeyMapping(PlayerAction.values().collectEntries { it.keys.collectEntries { key -> [(key): it.toString()] } })
                .build()
        player.gun = Pistol.create(pos, player.orientation)
        player.setOrientation(new Vector(x: 0, y: 50.0))
        return player
    }

    @Override
    void setOrientation(Vector orientation) {
        super.setOrientation(orientation)
        if (gun) {
            gun.setOrientation(orientation)
        }
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
        shoot(activeActions)
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
        updatePos(new Vector(x: newX, y: newY))
    }

    private updatePos(Vector pos) {
        body.center = pos
        if (gun) {
            gun.body.center = pos
        }
    }

    private updateOrientation(List<PlayerAction> activeActions, Long timestamp, Long delta) {
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
        def turnDirection = getTurnDirection(goal)
        def turnAngle = turnDirection * MathConstants.PI / 60

        orientation = orientation.rotate(turnAngle)
    }

    private getTurnDirection(Vector goal) {
        def perpendicularToOrientation = orientation.rotate(MathConstants.HALF_PI)
        def goalDot = perpendicularToOrientation.dot(goal)

        // Special cases, goal is either in opposite or in same direction.
        if (goalDot.abs() <= 1e-2) {
            // Opposite direction of current direction, turn direction is arbitrary.
            if (orientation.dot(goal) < 0) {
                return -1
            }
            // No turn because goal is already reached.
            return 0
        }

        // Regular case, turn in the closest direction of the goal.
        return goalDot > 0 ? 1 : -1
    }

    private shoot(List<PlayerAction> activeActions) {
        if (!activeActions.contains(PlayerAction.SHOOT)) {
            return
        }
        gun.shoot()
    }
}
