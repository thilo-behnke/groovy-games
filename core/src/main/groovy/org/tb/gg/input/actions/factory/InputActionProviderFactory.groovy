package org.tb.gg.input.actions.factory

import org.tb.gg.di.Inject
import org.tb.gg.input.actions.InputActionProvider
import org.tb.gg.input.actions.InputActionRegistry
import org.tb.gg.input.awt.KeyEventAwtAdapter

import javax.swing.JFrame

enum InputActionProviderType {
    AWT
}

class AwtInputActionProviderArgs {
    JFrame jFrame
    Set<String> actions
}

@Inject(service = 'EnvironmentService')
class AbstractInputActionProviderFactory {
    interface InputActionProviderFactory<T> {
        InputActionProvider createProvider(T providerArgs)
    }

    static class AwtInputActionProviderFactory implements InputActionProviderFactory<AwtInputActionProviderArgs> {
        @Override
        InputActionProvider createProvider(AwtInputActionProviderArgs providerArgs) {
            def keyEventSubject = new KeyEventAwtAdapter(providerArgs.jFrame)
            def inputActionRegistry = new InputActionRegistry(providerArgs.actions)
            return new InputActionProvider(inputActionRegistry, keyEventSubject)
        }
    }

    // TODO: This should be done once at the beginning, once the environment is determined. After that, the correct factory is a static attribute.
    static factory(InputActionProviderType type) {
        switch (type) {
            case InputActionProviderType.AWT:
                return new AwtInputActionProviderFactory()
            default:
                throw new Exception("Factory InputActionProviderType for ${type} not implemented.")
        }
    }
}
