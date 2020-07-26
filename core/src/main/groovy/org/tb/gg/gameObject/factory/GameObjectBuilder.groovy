package org.tb.gg.gameObject.factory

import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.InputComponent
import org.tb.gg.gameObject.components.RenderComponent
import org.tb.gg.global.util.Builder

class GameObjectBuilder implements Builder<GameObject> {

    private GameObject gameObject

    GameObjectBuilder(Class<? extends GameObject> clazz) {
        gameObject = clazz.getConstructor().newInstance()
    }

    GameObjectBuilder setRenderComponent(RenderComponent renderComponent) {
        gameObject.renderComponent = renderComponent
        return this
    }

    GameObjectBuilder setInputComponent(InputComponent inputComponent) {
        gameObject.inputComponent = inputComponent
        return this
    }

    GameObject build() {
        if(!gameObject.renderComponent || !gameObject.inputComponent) {
            throw new IllegalStateException("A game object must have a render and an input component!")
        }
        return gameObject
    }
}
