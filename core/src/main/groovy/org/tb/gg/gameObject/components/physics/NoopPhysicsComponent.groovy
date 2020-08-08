package org.tb.gg.gameObject.components.physics

import org.tb.gg.input.actions.NoopInputActionProvider

class NoopPhysicsComponent extends PhysicsComponent {
    private static final comp = new NoopPhysicsComponent()

    private NoopPhysicsComponent() {}

// Component does nothing, so just share it between components instead of creating it multiple times.
    static get() {
        return comp
    }
}
