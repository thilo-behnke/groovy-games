package org.tb.gg.gameObject.components

import org.tb.gg.input.actions.KeyPressInputActionProvider

class KeyboardInputComponent extends InputComponent {
    KeyboardInputComponent(KeyPressInputActionProvider inputActionProvider) {
        super(inputActionProvider)
    }
}
