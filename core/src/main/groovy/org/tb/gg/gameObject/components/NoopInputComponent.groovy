package org.tb.gg.gameObject.components

import org.tb.gg.input.actions.KeyPressInputActionProvider
import org.tb.gg.input.actions.NoopInputActionProvider

class NoopInputComponent extends InputComponent {
    NoopInputComponent() {
        super(new NoopInputActionProvider())
    }
}
