package org.tb.gg.gameObject.component.guns

import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.components.physics.CollisionSettings
import org.tb.gg.gameObject.components.physics.PhysicStats
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.global.geom.Vector

class BulletPhysicsComponent extends PhysicsComponent {
    private BulletPhysicsComponent(CollisionSettings collisionSettings, PhysicStats physicStats) {
        super(collisionSettings, physicStats)
    }

    static BulletPhysicsComponent create(Vector startVelocity) {
        def physicsComp = new BulletPhysicsComponent(
                new CollisionSettings(
                        collisionGroup: ShooterCollisionGroup.PLAYER_BULLET.toString(),
                        collidesWithGroups: [
                                ShooterCollisionGroup.ENEMY.toString(),
                        ].toSet()
                ),
                new PhysicStats(velocity: startVelocity)
        )
        return physicsComp
    }
}
