package org.tb.gg.gameObject.component.guns


import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.traits.TimePerishable
import org.tb.gg.global.geom.Vector

class BulletGameObject extends GameObject implements TimePerishable {
    private final static TIME_TO_LIVE_MS = 2_000
    Long spawnedAt

    static BulletGameObject create(Long timestamp, Vector pos, Vector orientation) {
        def physicsComp = new BulletPhysicsComponent(orientation)
        physicsComp.setCollisionGroups([ShooterCollisionGroup.ENEMIES.toString()].toSet())
        def bullet = (BulletGameObject) new GameObjectBuilder<>(BulletGameObject)
                .setBody(new ShapeBody(new Rect(pos, new Vector(x: 10, y: 10))))
                .setInputComponent(NoopInputComponent.get())
                .setRenderComponent(new BulletRenderComponent())
                .setPhysicsComponent(physicsComp)
                .build()
        bullet.setOrientation(orientation)
        bullet.setSpawnedAt(timestamp)
        return bullet
    }

    @Override
    void update(Long timestamp, Long delta) {
        super.update(timestamp, delta)

        physicsComponent.update(timestamp, delta)

    }

    @Override
    boolean shouldPerish(Long timestamp, Long delta) {
        return timestamp - spawnedAt > TIME_TO_LIVE_MS
    }
}
