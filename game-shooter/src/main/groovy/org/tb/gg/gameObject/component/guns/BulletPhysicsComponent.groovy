package org.tb.gg.gameObject.component.guns

import org.tb.gg.gameObject.components.physics.PhysicsComponent

class BulletPhysicsComponent extends PhysicsComponent {
    @Override
    boolean shouldCollide() {
        return true
    }
}
