package org.tb.gg.gameObject.component.player

import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.components.physics.PhysicsComponent

class PlayerPhysicsComponent extends PhysicsComponent {
    PlayerPhysicsComponent() {
        setCollisionGroups([
                ShooterCollisionGroup.ENEMIES
        ].collect { it.toString() }.toSet())
    }
}
