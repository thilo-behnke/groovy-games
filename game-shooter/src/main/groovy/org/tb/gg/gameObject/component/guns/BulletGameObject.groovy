package org.tb.gg.gameObject.component.guns

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
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
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.resources.ShooterGameResource

@PerishAfterTTL(10_000L)
@PerishWhenOutOfBounds
class BulletGameObject extends BaseGameObject implements TimePerishable, OutOfBoundsPerishable {
    @Delegate
    BulletProperties bulletProperties = new BulletProperties()
    @Inject
    static EnvironmentService environmentService

    static BulletGameObject create(Vector center, Vector orientation) {
        def physicsComp = BulletPhysicsComponent.create(orientation)
        def spriteBody = new SpriteBodyFactory().fromResource(ShooterGameResource.PROJECTILE_BLUE.name())
        def bullet = (BulletGameObject) new GameObjectBuilder<>(BulletGameObject)
                .setBody(spriteBody)
                .setInputComponent(NoopInputComponent.get())
                .setPhysicsComponent(physicsComp)
                .build()


        bullet.setOrientation(orientation)
        bullet.body.setCenter(center)
        bullet.body.shape.rotate(new Vector(x: 1, y: 0).angleBetween(orientation))

        environmentService.environment.renderDestination.drawCircle(bullet.body.shape.boundingRect.topLeft, 10.0, new RenderOptions(drawColor: DrawColor.GREEN))

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