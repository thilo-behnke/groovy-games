import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.Keyboard
import org.tb.gg.gameObject.components.KeyboardInputComponent
import org.tb.gg.gameObject.components.KeyboardRenderComponent
import org.tb.gg.gameObject.factory.KeyBoundGameObjectBuilder
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.Key

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def gameObjectProvider = new GameObjectProvider()
def defaultScene = new DefaultGameScene('default', gameObjectProvider)

def keyboard = new KeyBoundGameObjectBuilder(Keyboard)
        .setRenderComponent(
                new KeyboardRenderComponent(pos: Vector.unitVector() * 500.0)
        )
        .setActions(
                (HashSet<String>) ['W', 'A', 'S', 'D']
        )
        .setInputComponentClass(KeyboardInputComponent.class)
        .setDefaultKeyMapping([
                (Key.W): 'W',
                (Key.A): 'A',
                (Key.S): 'S',
                (Key.D): 'D'
        ])
        .build()

def keyboard2 = new KeyBoundGameObjectBuilder(Keyboard)
        .setRenderComponent(
                new KeyboardRenderComponent(pos: Vector.unitVector() * new Vector(x: 800.0, y: 500.0))
        )
        .setActions(
                (HashSet<String>) ['W', 'A', 'S', 'D']
        )
        .setInputComponentClass(KeyboardInputComponent.class)
        .setDefaultKeyMapping([
                (Key.UP)   : 'W',
                (Key.LEFT) : 'A',
                (Key.DOWN) : 'S',
                (Key.RIGHT): 'D'
        ])
        .build()

gameObjectProvider << keyboard
gameObjectProvider << keyboard2

gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
