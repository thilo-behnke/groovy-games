package org.tb.gg.gameObject.component.guns

import org.tb.gg.gameObject.component.ShooterCollisionGroup
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.global.geom.Vector

class BulletPhysicsComponent extends PhysicsComponent {

    BulletPhysicsComponent(Vector orientation) {
        velocity = orientation
    }

}
