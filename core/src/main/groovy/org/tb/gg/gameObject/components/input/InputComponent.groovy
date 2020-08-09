package org.tb.gg.gameObject.components.input

import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.input.actions.InputActionProvider

class InputComponent implements Lifecycle {
    private final InputActionProvider inputActionProvider

    InputComponent(InputActionProvider inputActionProvider) {
        this.inputActionProvider = inputActionProvider
    }

    Set<String> getActiveActions() {
        return this.inputActionProvider.activeActions()
    }

    @Override
    void init() {
        inputActionProvider.init()
    }

    @Override
    void destroy() {
        inputActionProvider.destroy()
    }
}
