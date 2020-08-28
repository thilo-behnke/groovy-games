package org.tb.gg.gameObject.component.guns


import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.PerishAfterTTL
import org.tb.gg.gameObject.PerishWhenOutOfBounds
import org.tb.gg.gameObject.components.body.SpriteBody
import org.tb.gg.gameObject.components.body.SpriteBodyFactory
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.components.render.DefaultRenderComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.traits.OutOfBoundsPerishable
import org.tb.gg.gameObject.traits.TimePerishable
import org.tb.gg.global.geom.Vector
import org.tb.gg.resources.ShooterGameResource

@PerishAfterTTL(10_000L)
@PerishWhenOutOfBounds
class BulletGameObject extends BaseGameObject implements TimePerishable, OutOfBoundsPerishable {
    @Delegate
    BulletProperties bulletProperties = new BulletProperties()

    static BulletGameObject create(Vector pos, Vector orientation) {
        def physicsComp = BulletPhysicsComponent.create(orientation)
        def spriteBody = new SpriteBodyFactory().fromResource(ShooterGameResource.PROJECTILE_BLUE.name())
        def bullet = (BulletGameObject) new GameObjectBuilder<>(BulletGameObject)
                .setBody(spriteBody)
                // TODO: Add custom render component to rotate bullet image depending orientation.
                .setInputComponent(NoopInputComponent.get())
                .setPhysicsComponent(physicsComp)
                .build()
        bullet.setOrientation(orientation)
        bullet.body.shape.setCenter(pos)
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