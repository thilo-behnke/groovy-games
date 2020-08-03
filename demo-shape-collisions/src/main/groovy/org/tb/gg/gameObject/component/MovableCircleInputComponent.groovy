package org.tb.gg.gameObject.component

import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.input.actions.KeyPressInputActionProvider

class MovableCircleInputComponent extends InputComponent {
    MovableCircleInputComponent(KeyPressInputActionProvider inputActionProvider) {
        super(inputActionProvider)
    }
}
