package org.tb.gg.gameObject.component

import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.input.actions.KeyPressInputActionProvider

class MovableShapeInputComponent extends InputComponent {
    MovableShapeInputComponent(KeyPressInputActionProvider inputActionProvider) {
        super(inputActionProvider)
    }
}
