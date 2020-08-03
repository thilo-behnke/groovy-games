package org.tb.gg.gameObject.components.input

import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.input.actions.NoopInputActionProvider

class NoopInputComponent extends InputComponent {
    NoopInputComponent() {
        super(new NoopInputActionProvider())
    }
}
