package org.tb.gg.gameObject.components

import org.tb.gg.input.actions.InputActionProvider
import org.tb.gg.input.actions.KeyPressInputActionProvider

abstract class InputComponent {
    private final InputActionProvider inputActionProvider

    InputComponent(InputActionProvider inputActionProvider) {
        this.inputActionProvider = inputActionProvider
    }

    Set<String> getActiveActions() {
        return this.inputActionProvider.activeActions()
    }
}
