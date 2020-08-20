import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.GameScene
import org.tb.gg.engine.Game
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.player.Clock

class ClockRenderEntryPoint implements Game {

    @Override
    void runGame() {
        def clock = Clock.create()

        def defaultScene = new GameScene('default')
        defaultScene.accessGameObjectProvider() << clock

        addScene(defaultScene, true)

        gameEngine.start()
    }
}

def game = new ClockRenderEntryPoint()
game.runGame()