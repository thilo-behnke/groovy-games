package org.tb.gg.gameObject.components.input

import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.input.actions.NoopInputActionProvider

class NoopInputComponent extends InputComponent {
    private static final comp = new NoopInputComponent()

    private NoopInputComponent() {
        super(new NoopInputActionProvider())
    }

    // Component does nothing, so just share it between components instead of creating it multiple times.
    static get() {
        return comp
    }
}
