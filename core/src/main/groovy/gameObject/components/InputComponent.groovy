package gameObject.components

import input.actions.InputActionProvider

abstract class InputComponent {
    @Delegate
    final InputActionProvider inputActionProvider

    InputComponent(InputActionProvider inputActionProvider) {
        this.inputActionProvider = inputActionProvider
    }
}
