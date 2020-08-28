package org.tb.gg.gameObject.component.guns


import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.PerishAfterTTL
import org.tb.gg.gameObject.PerishWhenOutOfBounds
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.traits.OutOfBoundsPerishable
import org.tb.gg.gameObject.traits.TimePerishable
import org.tb.gg.global.geom.Vector

@PerishAfterTTL(10_000L)
@PerishWhenOutOfBounds
class BulletGameObject extends BaseGameObject implements TimePerishable, OutOfBoundsPerishable {
    @Delegate
    BulletProperties bulletProperties = new BulletProperties()

    static BulletGameObject create(Vector pos, Vector orientation) {
        def physicsComp = BulletPhysicsComponent.create(orientation)
        def bullet = (BulletGameObject) new GameObjectBuilder<>(BulletGameObject)
                .setBody(new ShapeBody(new Rect(pos, new Vector(x: 10, y: 10))))
                .setInputComponent(NoopInputComponent.get())
                .setRenderComponent(new BulletRenderComponent())
                .setPhysicsComponent(physicsComp)
                .build()
        bullet.setOrientation(orientation)
        return bullet
    }

    @Override
    void update(Long timestamp, Long delta) {
        super.update(timestamp, delta)

        physicsComponent.update(timestamp, delta)
    }
}

class BulletProperties {
    int damage
}