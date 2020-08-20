package org.tb.gg.gameObject.component.player

import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.components.physics.PhysicsComponent

class PlayerPhysicsComponent extends PhysicsComponent {
    PlayerPhysicsComponent() {
        setCollisionGroup(ShooterCollisionGroup.PLAYER.toString())
        setCollidesWithGroups([
                ShooterCollisionGroup.ENEMY,
                ShooterCollisionGroup.ENEMY_BULLET
        ].collect { it.toString() }.toSet())
    }
}
