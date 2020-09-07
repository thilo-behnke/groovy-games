package org.tb.gg.gameObject.component.player

import org.tb.gg.di.Inject
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.component.guns.Gun
import org.tb.gg.gameObject.component.guns.GunWheel
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.components.body.SpriteBodyFactory
import org.tb.gg.gameObject.factory.KeyBoundGameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants
import org.tb.gg.resources.ShooterGameResource

class PlayerGameObject extends BaseGameObject {
    @Inject
    GunWheel gunWheel

    private Gun gun

    private long SWITCH_GUN_COOLDOWN_MS = 200
    private long lastWeaponSwitchTimestamp = 0

    static PlayerGameObject create() {
        def pos = new Vector(x: 100, y: 100)
        def orientation = new Vector(x: 0, y: 50.0)
        def body = new SpriteBodyFactory().fromResource(ShooterGameResource.SPACESHIP_GREEN.name())
        body.center = pos
        def player = (PlayerGameObject) new KeyBoundGameObjectBuilder(PlayerGameObject)
                .setBody(body)
                .setInputComponentClass(PlayerInputComponent)
                .setRenderComponent(new PlayerRenderComponent())
                .setPhysicsComponent(PlayerPhysicsComponent.create())
                .setActions(PlayerAction.values().collect { it.toString() }.toSet())
                .setDefaultKeyMapping(PlayerAction.values().collectEntries { it.keys.collectEntries { key -> [(key): it.toString()] } })
                .build()

        player.setOrientation(orientation)
        player.switchGun()
        return player
    }

    @Override
    void setOrientation(Vector orientation) {
        super.setOrientation(orientation)
        if (gun) {
            updateGunPosition()
        }
    }

    @Override
    void update(Long timestamp, Long delta) {
        super.update(timestamp, delta)

        def activeActions = inputComponent.getActiveActions().collect { PlayerAction.valueOf(it) }
        if (activeActions.isEmpty()) {
            return
        }

        updateVelocity(activeActions)
        updateOrientation(activeActions, timestamp, delta)
        physicsComponent.update(timestamp, delta)
        updatePos()
        switchGun(activeActions, timestamp)
        shoot(activeActions)
    }

    private updateVelocity(List<PlayerAction> activeActions) {
        def newX = 0
        def newY = 0
        // Update X.
        if (activeActions.contains(PlayerAction.MOVE_RIGHT)) {
            newX = 1
        } else if (activeActions.contains(PlayerAction.MOVE_LEFT)) {
            newX = -1
        }
        // Update Y.
        if (activeActions.contains(PlayerAction.MOVE_UP)) {
            newY = 1
        } else if (activeActions.contains(PlayerAction.MOVE_DOWN)) {
            newY = -1
        }
        physicsComponent.setVelocity(new Vector(x: newX, y: newY))
    }

    private updatePos() {
        if (gun) {
            updateGunPosition()
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

    private switchGun(List<PlayerAction> activeActions, long timestamp) {
        if (!activeActions.contains(PlayerAction.SWITCH_WEAPON) || timestamp - lastWeaponSwitchTimestamp < SWITCH_GUN_COOLDOWN_MS) {
            return
        }
        lastWeaponSwitchTimestamp = timestamp
        switchGun()
    }

    private switchGun() {
        setGun(gunWheel.nextGun())
    }

    void setGun(Gun gun) {
        this.gun = gun
        updateGunPosition()
    }

    void updateGunPosition() {
        gun.setOrientation(orientation)
        gun.body.shape.setCenter(body.center + orientation.normalize() * body.shape.boundingRect.diagonalLength())
    }

}
