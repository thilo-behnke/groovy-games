package org.tb.gg.gameObject

import org.tb.gg.input.actions.factory.AbstractInputActionProviderFactory
import org.tb.gg.input.actions.factory.InputActionProviderType

class Keyboard extends GameObject {

    static create() {
        def keyboard = new Keyboard()
        keyboard.renderComponent = new KeyboardRenderComponent()
        // TODO: How to make this independent of awt or the jframe needed to add the listener? Should this be a shared final object that is initialized at the beginning and passed around?
        def inputProvider = AbstractInputActionProviderFactory.factory(InputActionProviderType.AWT).createProvider()
    }
}
