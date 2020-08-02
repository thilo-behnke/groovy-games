package org.tb.gg.gameObject.components

import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.input.actions.InputActionProvider

abstract class InputComponent implements Lifecycle {
    private final InputActionProvider inputActionProvider

    InputComponent(InputActionProvider inputActionProvider) {
        this.inputActionProvider = inputActionProvider
    }

    Set<String> getActiveActions() {
        return this.inputActionProvider.activeActions()
    }

    @Override
    void onInit() {
        inputActionProvider.onInit()
    }

    @Override
    void onDestroy() {
        inputActionProvider.onDestroy()
    }
}
