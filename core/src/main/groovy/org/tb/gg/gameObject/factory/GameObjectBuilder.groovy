package org.tb.gg.gameObject.factory


import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.render.DefaultRenderComponent
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.global.util.Builder

class GameObjectBuilder<T extends BaseGameObject> implements Builder<BaseGameObject> {

    private BaseGameObject gameObject

    GameObjectBuilder(Class<T> clazz) {
        gameObject = clazz.getConstructor().newInstance()
    }

    GameObjectBuilder setBody(ShapeBody body) {
        gameObject.body = body
        return this
    }

    GameObjectBuilder setRenderComponent(RenderComponent renderComponent) {
        gameObject.renderComponent = renderComponent
        return this
    }

    GameObjectBuilder setInputComponent(InputComponent inputComponent) {
        gameObject.inputComponent = inputComponent
        return this
    }

    GameObjectBuilder setPhysicsComponent(PhysicsComponent physicsComponent) {
        gameObject.physicsComponent = physicsComponent
        return this
    }

    T build() {
        if (!gameObject.renderComponent) {
            gameObject.renderComponent = new DefaultRenderComponent();
        }
        if (!gameObject.inputComponent) {
            gameObject.inputComponent = NoopInputComponent.get();
        }
        if (!gameObject.body) {
            throw new IllegalStateException("A game object must have a body!")
        }
        return (T) gameObject
    }
}
