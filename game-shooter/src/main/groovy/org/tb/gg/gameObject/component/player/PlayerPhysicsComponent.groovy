package org.tb.gg.gameObject.component.player

import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.components.physics.CollisionSettings
import org.tb.gg.gameObject.components.physics.PhysicStats
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.global.geom.Vector

class PlayerPhysicsComponent extends PhysicsComponent {

    PlayerPhysicsComponent(CollisionSettings collisionSettings, PhysicStats physicStats) {
        super(collisionSettings, physicStats)
    }

    static PlayerPhysicsComponent create() {
        new PlayerPhysicsComponent(
                new CollisionSettings(
                        collisionGroup: ShooterCollisionGroup.PLAYER.toString(),
                        collidesWithGroups: [
                                ShooterCollisionGroup.ENEMY,
                                ShooterCollisionGroup.ENEMY_BULLET
                        ].collect { it.toString() }.toSet()
                ),
                new PhysicStats(velocity: Vector.zeroVector())
        )
    }
}
