package org.tb.gg.gameObject.component.guns

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import org.tb.gg.di.Inject
import org.tb.gg.engine.SceneManager
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.EnvironmentSettings
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.world.WorldStateProvider

@EqualsAndHashCode(cache = true, includeFields = true, includes = ['org_tb_gg_gameObject_component_guns_Gun__props'])
class AutomaticGun extends BaseGameObject implements Gun<AutomaticGunProperties> {
    @Inject
    WorldStateProvider worldStateProvider
    @Inject
    SceneManager sceneManager

    static final PISTOL_PROPERTIES = new AutomaticGunProperties(gunType: GunType.PISTOL, COOL_DOWN_MS: 300, BULLET_DAMAGE: 40, BULLET_VELOCITY: 1.0)
    static final MACHINE_GUN_PROPERTIES = new AutomaticGunProperties(gunType: GunType.MACHINE_GUN, COOL_DOWN_MS: 100, BULLET_DAMAGE: 25, BULLET_VELOCITY: 2.0)

    private long lastShotTimestamp = 0

    @Inject
    EnvironmentService environmentService

    @Override
    void shoot() {
        def timestamp = worldStateProvider.get().currentLoopTimestamp
        if (timestamp - lastShotTimestamp < props.COOL_DOWN_MS) {
            return
        }

        lastShotTimestamp = timestamp

        def bulletSpawnPosition = body.center
        environmentService.environment.renderDestination.drawCircle(bulletSpawnPosition, 20.0, new RenderOptions(drawColor: DrawColor.RED))
        def bullet = BulletGameObject.create(bulletSpawnPosition, orientation.normalize() * props.BULLET_VELOCITY)
        bullet.setDamage(props.BULLET_DAMAGE)

        sceneManager.getActiveScene().ifPresent { it.accessGameObjectProvider().addGameObject(bullet) }
    }
}

@Immutable
class AutomaticGunProperties extends GunSpecificProps {
    int COOL_DOWN_MS
    int BULLET_DAMAGE
    BigDecimal BULLET_VELOCITY
}

