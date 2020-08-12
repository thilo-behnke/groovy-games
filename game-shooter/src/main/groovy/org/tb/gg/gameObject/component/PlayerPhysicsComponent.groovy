package org.tb.gg.gameObject.component

import org.tb.gg.gameObject.components.physics.PhysicsComponent

class PlayerPhysicsComponent extends PhysicsComponent {
    @Override
    boolean shouldCollide() {
        return true
    }
}
