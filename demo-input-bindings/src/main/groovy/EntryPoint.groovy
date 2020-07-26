import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.Keyboard
import org.tb.gg.gameObject.components.InputComponent
import org.tb.gg.gameObject.components.KeyboardInputComponent
import org.tb.gg.gameObject.components.KeyboardRenderComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.Key
import org.tb.gg.input.actions.factory.AbstractInputActionProviderFactory
import org.tb.gg.input.actions.factory.InputActionProviderArgs

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def gameObjectProvider = new GameObjectProvider()
def defaultScene = new DefaultGameScene('default', gameObjectProvider)

def keyBoardInputActionProvider = AbstractInputActionProviderFactory.factory().createProvider(
        new InputActionProviderArgs(actions: ['W', 'A', 'S', 'D'])
)
keyBoardInputActionProvider.overrideKeyMappings([
        (Key.W): 'W',
        (Key.A): 'A',
        (Key.S): 'S',
        (Key.D): 'D'
])
def keyBoard = new GameObjectBuilder(Keyboard.class)
        .setInputComponent(
                new KeyboardInputComponent(keyBoardInputActionProvider)
        )
        .setRenderComponent(
                new KeyboardRenderComponent(pos: Vector.unitVector() * 500.0)
        )
        .build()


def keyBoardInputActionProvider2 = AbstractInputActionProviderFactory.factory().createProvider(
        new InputActionProviderArgs(actions: ['W', 'A', 'S', 'D'])
)
keyBoardInputActionProvider2.overrideKeyMappings([
        (Key.UP): 'W',
        (Key.LEFT): 'A',
        (Key.DOWN): 'S',
        (Key.RIGHT): 'D'
])
def keyBoard2 = new GameObjectBuilder(Keyboard.class)
        .setInputComponent(
                new KeyboardInputComponent(keyBoardInputActionProvider2)
        )
        .setRenderComponent(
                new KeyboardRenderComponent(pos: Vector.unitVector() * new Vector(x: 800.0, y: 500.0))
        )
        .build()

gameObjectProvider << keyBoard
gameObjectProvider << keyBoard2

gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
