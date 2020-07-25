package gameObject.components

import input.actions.InputActionProvider

abstract class InputComponent {
    private final InputActionProvider inputActionProvider

    InputComponent(InputActionProvider inputActionProvider) {
        this.inputActionProvider = inputActionProvider
    }
}
