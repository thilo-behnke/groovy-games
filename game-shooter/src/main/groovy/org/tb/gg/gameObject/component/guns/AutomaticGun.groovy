package org.tb.gg.gameObject.component.guns

import groovy.transform.Immutable
import org.tb.gg.di.Inject
import org.tb.gg.engine.SceneManager
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import org.tb.gg.world.WorldStateProvider

class AutomaticGun extends BaseGameObject implements Gun<AutomaticGunProperties> {
    @Inject
    WorldStateProvider worldStateProvider
    @Inject
    SceneManager sceneManager

    static final PISTOL_PROPERTIES = new AutomaticGunProperties(COOL_DOWN_MS: 300, BULLET_DAMAGE: 20, BULLET_VELOCITY: 1.0)
    static final MACHINE_GUN_PROPERTIES = new AutomaticGunProperties(COOL_DOWN_MS: 100, BULLET_DAMAGE: 10, BULLET_VELOCITY: 2.0)

    private long lastShotTimestamp = 0

    @Override
    void shoot() {
        def timestamp = worldStateProvider.get().currentLoopTimestamp
        if (timestamp - lastShotTimestamp < props.COOL_DOWN_MS) {
            return
        }

        lastShotTimestamp = timestamp

        def bullet = BulletGameObject.create(body.center, orientation.normalize() * props.BULLET_VELOCITY)
        bullet.setDamage(props.BULLET_DAMAGE)
        sceneManager.getActiveScene().ifPresent { it.accessGameObjectProvider().addGameObject(bullet) }
    }
}

@Immutable
class AutomaticGunProperties {
    int COOL_DOWN_MS
    int BULLET_DAMAGE
    BigDecimal BULLET_VELOCITY
}

