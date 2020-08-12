package org.tb.gg.gameObject.components.physics

import org.tb.gg.input.actions.NoopInputActionProvider

class NoopPhysicsComponent extends PhysicsComponent {
    @Override
    boolean shouldCollide() {
        return false
    }
}
