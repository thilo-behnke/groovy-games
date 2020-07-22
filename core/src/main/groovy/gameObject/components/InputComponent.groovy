package gameObject.components

import input.actions.InputActionProvider

abstract class InputComponent {
    private InputActionProvider inputActionProvider

    InputComponent(InputActionProvider inputActionProvider) {
        this.inputActionProvider = inputActionProvider
    }

    Set<String> activeActions() {
        inputActionProvider.activeActions()
    }
}
