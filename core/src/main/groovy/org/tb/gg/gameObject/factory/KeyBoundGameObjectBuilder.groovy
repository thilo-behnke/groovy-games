package org.tb.gg.gameObject.factory

import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.physics.ShapePhysicsComponent
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.global.util.Builder
import org.tb.gg.input.Key
import org.tb.gg.input.actions.InputActionProvider
import org.tb.gg.input.actions.KeyPressInputActionProvider
import org.tb.gg.input.actions.factory.AbstractInputActionProviderFactory
import org.tb.gg.input.actions.factory.InputActionProviderArgs

class KeyBoundGameObjectBuilder<T extends GameObject> implements Builder<GameObject> {
    private GameObject gameObject
    private KeyPressInputActionProvider keyPressInputActionProvider
    private Class<? extends InputComponent> inputComponentClazz
    private Map<Key, String> defaultKeyMapping

    KeyBoundGameObjectBuilder(Class<T> clazz) {
        gameObject = clazz.getConstructor().newInstance()
    }

    KeyBoundGameObjectBuilder setBody(ShapeBody body) {
        gameObject.body = body
        return this
    }

    KeyBoundGameObjectBuilder setRenderComponent(RenderComponent renderComponent) {
        gameObject.renderComponent = renderComponent
        return this
    }

    KeyBoundGameObjectBuilder setPhysicsComponent(ShapePhysicsComponent physicsComponent) {
        gameObject.physicsComponent = physicsComponent
        return this
    }

    KeyBoundGameObjectBuilder setInputComponentClass(Class<? extends InputComponent> clazz) {
        inputComponentClazz = clazz
        return this
    }

    KeyBoundGameObjectBuilder configureActions(Set<String> actions, Map<Key, String> defaultKeyMapping) {
        setActions(actions)
        setDefaultKeyMapping(defaultKeyMapping)
        return this
    }

    KeyBoundGameObjectBuilder setActions(Set<String> actions) {
        keyPressInputActionProvider = AbstractInputActionProviderFactory.factory().createProvider(
                new InputActionProviderArgs(actions: actions)
        )
        return this
    }

    KeyBoundGameObjectBuilder setDefaultKeyMapping(Map<Key, String> keyMapping) {
        this.defaultKeyMapping = keyMapping
        return this
    }

    @Override
    T build() {
        if (!gameObject.renderComponent || !keyPressInputActionProvider || !inputComponentClazz) {
            throw new IllegalStateException("A key bound game object must have a render component, actions and input component class!")
        }
        if (!defaultKeyMapping) {
            defaultKeyMapping = new HashMap<>()
        }
        keyPressInputActionProvider.overrideKeyMappings(defaultKeyMapping)
        def inputComponent = inputComponentClazz.getConstructor(KeyPressInputActionProvider).newInstance(keyPressInputActionProvider)
        gameObject.inputComponent = inputComponent
        return (T) gameObject
    }
}
