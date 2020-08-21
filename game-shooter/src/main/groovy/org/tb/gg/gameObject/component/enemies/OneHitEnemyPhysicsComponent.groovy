package org.tb.gg.gameObject.component.enemies

import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.components.physics.CollisionSettings
import org.tb.gg.gameObject.components.physics.PhysicStats
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.global.geom.Vector

class OneHitEnemyPhysicsComponent extends PhysicsComponent {
    private OneHitEnemyPhysicsComponent(CollisionSettings collisionSettings, PhysicStats physicStats) {
        super(collisionSettings, physicStats)
    }

    static OneHitEnemyPhysicsComponent create(Vector velocity) {
        def physicsComp = new OneHitEnemyPhysicsComponent(
                new CollisionSettings(
                        collisionGroup: ShooterCollisionGroup.ENEMY.toString(),
                        collidesWithGroups: [
                                ShooterCollisionGroup.PLAYER_BULLET.toString(),
                                ShooterCollisionGroup.PLAYER.toString(),
                        ].toSet()
                ),
                new PhysicStats(velocity: Vector.zeroVector())
        )
        return physicsComp

    }

}
