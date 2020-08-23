package org.tb.gg.gameObject.component.guns

import org.tb.gg.di.Inject
import org.tb.gg.engine.SceneManager
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import org.tb.gg.world.WorldStateProvider

class Pistol extends BaseGameObject implements Gun {
    @Inject
    WorldStateProvider worldStateProvider
    @Inject
    SceneManager sceneManager

    private static final COOL_DOWN_MS = 300
    private static final BULLET_DAMAGE = 20
    private static final BULLET_VELOCITY = 1.0

    private long lastShotTimestamp = 0

    static Pistol create(Vector pos, Vector orientation) {
        def pistol = (Pistol) new GameObjectBuilder<>(Pistol)
                .setBody(new ShapeBody(new Rect(pos, new Vector(x: 10, y: 10))))
                .build()
        pistol.setOrientation(orientation)
        return pistol
    }

    @Override
    void shoot() {
        def timestamp = worldStateProvider.get().currentLoopTimestamp
        if (timestamp - lastShotTimestamp < COOL_DOWN_MS) {
            return
        }

        lastShotTimestamp = timestamp

        def bullet = BulletGameObject.create(body.center, orientation.normalize() * BULLET_VELOCITY)
        bullet.setDamage(BULLET_DAMAGE)
        sceneManager.getActiveScene().ifPresent { it.accessGameObjectProvider().addGameObject(bullet) }
    }
}
