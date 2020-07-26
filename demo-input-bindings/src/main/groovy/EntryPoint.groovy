import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.components.InputComponent
import org.tb.gg.gameObject.components.KeyboardInputComponent
import org.tb.gg.gameObject.components.KeyboardRenderComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.input.actions.factory.AbstractInputActionProviderFactory
import org.tb.gg.input.actions.factory.InputActionProviderArgs

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def gameObjectProvider = new GameObjectProvider()
def defaultScene = new DefaultGameScene('default', gameObjectProvider)

def keyBoard = new GameObjectBuilder()
        .setInputComponent(
                new KeyboardInputComponent(
                        AbstractInputActionProviderFactory.factory().createProvider(
                                new InputActionProviderArgs(actions: [])
                        )
                )
        )
        .setRenderComponent(
                new KeyboardRenderComponent()
        )
        .build()

gameObjectProvider << keyBoard

gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
