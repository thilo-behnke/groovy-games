package org.tb.gg.gameObject.components

import org.tb.gg.input.actions.KeyPressInputActionProvider

abstract class InputComponent {
    private final KeyPressInputActionProvider inputActionProvider

    InputComponent(KeyPressInputActionProvider inputActionProvider) {
        this.inputActionProvider = inputActionProvider
    }

    Set<String> getActiveActions() {
        return this.inputActionProvider.activeActions()
    }
}
