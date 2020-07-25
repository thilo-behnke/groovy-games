package org.tb.gg.gameObject.components

import org.tb.gg.input.actions.InputActionProvider

abstract class InputComponent {
    private final InputActionProvider inputActionProvider

    InputComponent(InputActionProvider inputActionProvider) {
        this.inputActionProvider = inputActionProvider
    }
}
