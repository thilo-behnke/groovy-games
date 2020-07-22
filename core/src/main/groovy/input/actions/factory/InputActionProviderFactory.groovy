package input.actions.factory

import input.actions.InputActionProvider
import input.actions.InputActionRegistry
import input.keyEvent.KeyEventJwtAdapter

import javax.swing.JFrame

enum InputActionProviderType {
    AWT
}

class AwtInputActionProviderArgs {
    JFrame jFrame
}

class AbstractInputActionProviderFactory {
    interface InputActionProviderFactory<T> {
        InputActionProvider createProvider(T providerArgs)
    }

    static class AwtInputActionProviderFactory implements InputActionProviderFactory<AwtInputActionProviderArgs> {
        @Override
        InputActionProvider createProvider(AwtInputActionProviderArgs providerArgs) {
            def keyEventSubject = new KeyEventJwtAdapter(providerArgs.jFrame)
            return new InputActionProvider(new InputActionRegistry(), keyEventSubject)
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
