package org.tb.gg.gameObject.component.enemies

import org.tb.gg.collision.CollisionType
import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.components.physics.CollisionDefinition
import org.tb.gg.gameObject.components.physics.CollisionSettings
import org.tb.gg.gameObject.components.physics.PhysicStats
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.global.geom.Vector

class OneHitEnemyPhysicsComponent extends PhysicsComponent {
    private OneHitEnemyPhysicsComponent(CollisionSettings collisionSettings, PhysicStats physicStats) {
        super()
        setCollisionSettings(collisionSettings)
        setPhysicStats(physicStats)
    }

    static OneHitEnemyPhysicsComponent create(Vector velocity) {
        def physicsComp = new OneHitEnemyPhysicsComponent(
                new CollisionSettings(
                        collisionGroup: ShooterCollisionGroup.ENEMY.toString(),
                        collidesWithGroups: [
                                new CollisionDefinition(collisionGroup:  ShooterCollisionGroup.PLAYER_BULLET.toString(), collisionType: CollisionType.SOLID),
                                new CollisionDefinition(collisionGroup:  ShooterCollisionGroup.PLAYER.toString(), collisionType: CollisionType.SOLID),
                                new CollisionDefinition(collisionGroup:  ShooterCollisionGroup.ENEMY.toString(), collisionType: CollisionType.SOLID),
                        ].toSet()
                ),
                new PhysicStats(velocity: Vector.zeroVector())
        )
        return physicsComp

    }

}
