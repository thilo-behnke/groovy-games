package org.tb.gg.gameObject.component.guns

import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector

class BulletGameObject extends GameObject {
    static BulletGameObject create(Vector pos, Vector orientation) {
        def bullet = (BulletGameObject) new GameObjectBuilder<>(BulletGameObject)
                .setBody(new ShapeBody(new Rect(pos, new Vector(x: 10, y: 10))))
                .setInputComponent(NoopInputComponent.get())
                .setRenderComponent(new BulletRenderComponent())
                .setPhysicsComponent(new BulletPhysicsComponent(orientation))
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
