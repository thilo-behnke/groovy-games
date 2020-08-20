import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.Game
import org.tb.gg.engine.GameEngine
import org.tb.gg.engine.GameScene
import org.tb.gg.gameObject.Keyboard
import org.tb.gg.gameObject.components.KeyboardInputComponent
import org.tb.gg.gameObject.components.KeyboardRenderComponent
import org.tb.gg.gameObject.factory.KeyBoundGameObjectBuilder
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.Key

class InputBindingsEntrypoint implements Game {

    @Override
    void runGame() {
        def defaultScene = new GameScene('default')

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

        defaultScene.accessGameObjectProvider() << keyboard
        defaultScene.accessGameObjectProvider() << keyboard2

        addScene(defaultScene, true)

        gameEngine.start()
    }
}

def game = new InputBindingsEntrypoint()
game.runGame()
