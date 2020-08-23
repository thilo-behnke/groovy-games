package org.tb.gg.gameObject.component.player

import org.tb.gg.collision.CollisionType
import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.components.physics.CollisionDefinition
import org.tb.gg.gameObject.components.physics.CollisionSettings
import org.tb.gg.gameObject.components.physics.PhysicStats
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.global.geom.Vector

class PlayerPhysicsComponent extends PhysicsComponent {

    PlayerPhysicsComponent(CollisionSettings collisionSettings, PhysicStats physicStats) {
        super()
        setCollisionSettings(collisionSettings)
        setPhysicStats(physicStats)
    }

    static PlayerPhysicsComponent create() {
        new PlayerPhysicsComponent(
                new CollisionSettings(
                        collisionGroup: ShooterCollisionGroup.PLAYER.toString(),
                        collidesWithGroups: [
                                new CollisionDefinition(collisionGroup: ShooterCollisionGroup.ENEMY, collisionType: CollisionType.SOLID),
                                new CollisionDefinition(collisionGroup: ShooterCollisionGroup.ENEMY_BULLET, collisionType: CollisionType.SOLID),
                        ].toSet()
                ),
                new PhysicStats(velocity: Vector.zeroVector())
        )
    }
}
