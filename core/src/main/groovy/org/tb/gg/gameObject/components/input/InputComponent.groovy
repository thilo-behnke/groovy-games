package org.tb.gg.gameObject.components.input

import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.input.actions.InputActionProvider

class InputComponent implements Lifecycle, Updateable {
    private final InputActionProvider inputActionProvider

    InputComponent(InputActionProvider inputActionProvider) {
        this.inputActionProvider = inputActionProvider
    }

    Set<String> getActiveActions() {
        return this.inputActionProvider.activeActions()
    }

    @Override
    void update(Long timestamp, Long delta) {
        inputActionProvider.update(timestamp, delta)
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
