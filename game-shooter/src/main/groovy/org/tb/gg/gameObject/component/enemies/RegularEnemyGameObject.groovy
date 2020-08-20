package org.tb.gg.gameObject.component.enemies

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.component.guns.BulletPhysicsComponent
import org.tb.gg.gameObject.component.guns.BulletRenderComponent
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.global.geom.Vector

class RegularEnemyGameObject extends BaseGameObject {
    static RegularEnemyGameObject create(Vector pos) {
        def physicsComp = new BulletPhysicsComponent(Vector.zeroVector())
        physicsComp.setCollisionGroup(ShooterCollisionGroup.ENEMY.toString())
        physicsComp.setCollidesWithGroups([
                ShooterCollisionGroup.PLAYER_BULLET.toString(),
        ].toSet())
        def bullet = (RegularEnemyGameObject) new GameObjectBuilder<>(RegularEnemyGameObject)
                .setBody(new ShapeBody(new Circle(center: pos, radius: 20)))
                .setPhysicsComponent(physicsComp)
                .build()
        bullet.setOrientation(Vector.zeroVector())
        return bullet
    }

    @Override
    void update(Long timestamp, Long delta) {
        super.update(timestamp, delta)

        physicsComponent.update(timestamp, delta)
    }
}
