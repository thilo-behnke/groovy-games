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
        def orientation = new Vector(x: 1, y: 0)
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
        // TODO: Introduce acceleration.
        if (activeActions.contains(PlayerAction.THRUST)) {
            if (physicsComponent.velocity == Vector.zeroVector()) {
                physicsComponent.velocity = orientation.scale(1.05)
            }
            def angleBetweenOrientationAndVelocity = orientation.angleBetween(physicsComponent.velocity)
            physicsComponent.velocity = physicsComponent.velocity.rotate(angleBetweenOrientationAndVelocity / 10)
        } else {
            physicsComponent.velocity = physicsComponent.velocity.scale(0.005)
        }
    }

    private updatePos() {
        if (gun) {
            updateGunPosition()
        }
    }

    private updateOrientation(List<PlayerAction> activeActions, Long timestamp, Long delta) {
        if (!activeActions.contains(PlayerAction.LOOK_LEFT) && !activeActions.contains(PlayerAction.LOOK_RIGHT)) {
            return
        }
        def turnDirection = activeActions.contains(PlayerAction.LOOK_LEFT) ? 1 : -1
        def turnAngle = (turnDirection * MathConstants.PI / 60).remainder(2 * MathConstants.PI)

        orientation = orientation.rotate(turnAngle)
        body.shape.rotate(turnAngle)
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
