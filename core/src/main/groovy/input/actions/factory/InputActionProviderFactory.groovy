package input.actions.factory

import input.Key
import input.actions.InputActionProvider
import input.actions.InputActionRegistry
import input.awt.KeyEventAwtAdapter

import javax.swing.JFrame

enum InputActionProviderType {
    AWT
}

class AwtInputActionProviderArgs {
    JFrame jFrame
    Set<String> actions
}

class AbstractInputActionProviderFactory {
    interface InputActionProviderFactory<T> {
        InputActionProvider createProvider(T providerArgs)
    }

    static class AwtInputActionProviderFactory implements InputActionProviderFactory<AwtInputActionProviderArgs> {
        @Override
        InputActionProvider createProvider(AwtInputActionProviderArgs providerArgs) {
            def keyEventSubject = new KeyEventAwtAdapter(providerArgs.jFrame)
            def inputActionRegistry = new InputActionRegistry()
            inputActionRegistry.registerActions(providerArgs.actions)
            return new InputActionProvider(inputActionRegistry, keyEventSubject)
        }
    }

    static factory(InputActionProviderType type) {
        switch (type) {
            case InputActionProviderType.AWT:
                return new AwtInputActionProviderFactory()
            default:
                throw new Exception("Factory InputActionProviderType for ${type} not implemented.")
        }
    }
}
