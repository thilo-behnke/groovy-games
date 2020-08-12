package org.tb.gg.gameObject.factory

import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.global.util.Builder
import org.tb.gg.input.Key
import org.tb.gg.input.actions.KeyPressInputActionProvider
import org.tb.gg.input.actions.factory.AbstractInputActionProviderFactory
import org.tb.gg.input.actions.factory.InputActionProviderArgs

class InputComponentBuilder implements Builder<InputComponent> {
    private KeyPressInputActionProvider keyPressInputActionProvider
    private Class<? extends InputComponent> inputComponentClazz
    private Map<Key, String> defaultKeyMapping

    InputComponentBuilder setInputComponentClass(Class<? extends InputComponent> clazz) {
        inputComponentClazz = clazz
        return this
    }

    InputComponentBuilder setActions(Set<String> actions) {
        keyPressInputActionProvider = new AbstractInputActionProviderFactory().factory().createProvider(
                new InputActionProviderArgs(actions: actions)
        )
        return this
    }

    InputComponentBuilder setDefaultKeyMapping(Map<Key, String> keyMapping) {
        this.defaultKeyMapping = keyMapping
        return this
    }

    @Override
    InputComponent build() {
        if (!keyPressInputActionProvider || !inputComponentClazz) {
            throw new IllegalStateException("A key bound game object must have a keyPressInputActionProvider and an inputComponentClazz!")
        }

        if (!defaultKeyMapping) {
            defaultKeyMapping = new HashMap<>()
        }
        keyPressInputActionProvider.overrideKeyMappings(defaultKeyMapping)
        return inputComponentClazz.getConstructor(KeyPressInputActionProvider).newInstance(keyPressInputActionProvider)
    }
}
