package org.tb.gg.input.actions.factory

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.Graphics
import org.tb.gg.input.actions.KeyPressInputActionProvider
import org.tb.gg.input.actions.InputActionRegistry
import org.tb.gg.input.awt.SwingKeyEventAdapter

class InputActionProviderArgs {
    Set<String> actions
}

class AbstractInputActionProviderFactory {
    @Inject private EnvironmentService environmentService

    interface InputActionProviderFactory<T> {
        KeyPressInputActionProvider createProvider(T providerArgs)
    }

    static class AwtInputActionProviderFactory implements InputActionProviderFactory<InputActionProviderArgs> {
        @Override
        KeyPressInputActionProvider createProvider(InputActionProviderArgs providerArgs) {
            def keyEventSubject = new SwingKeyEventAdapter()
            def inputActionRegistry = new InputActionRegistry(providerArgs.actions)
            def inputActionProvider = new KeyPressInputActionProvider(inputActionRegistry, keyEventSubject)
            return inputActionProvider
        }
    }

    // TODO: This should be done once at the beginning, once the environment is determined. After that, the correct factory is a static attribute.
    InputActionProviderFactory factory() {
        def type = environmentService.environment.graphics
        switch (environmentService.environment.graphics) {
            case Graphics.SWING:
                return new AwtInputActionProviderFactory()
            default:
                throw new Exception("Factory InputActionProviderType for ${type} not implemented.")
        }
    }
}
