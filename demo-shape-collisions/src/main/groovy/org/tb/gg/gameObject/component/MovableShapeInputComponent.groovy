package org.tb.gg.gameObject.component

import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.input.actions.KeyPressInputActionProvider

// TODO: What's the point of this component atm? The only difference seems to be the concrete typing of the input action provider.
// Will this later be extended to AI methods?
class MovableShapeInputComponent extends InputComponent {
    MovableShapeInputComponent(KeyPressInputActionProvider inputActionProvider) {
        super(inputActionProvider)
    }
}
