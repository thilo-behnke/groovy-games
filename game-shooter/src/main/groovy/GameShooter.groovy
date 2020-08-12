import org.tb.gg.GameEngineProvider
import org.tb.gg.di.Inject
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.Game
import org.tb.gg.engine.GameEngine
import org.tb.gg.engine.SceneManager
import org.tb.gg.gameObject.component.PlayerGameObject

class GameEntryPoint implements Game {

    @Inject
    private SceneManager sceneManager

    void runGame() {
        GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

        def defaultScene = new DefaultGameScene('default')

        def player = PlayerGameObject.create()

        defaultScene.accessGameObjectProvider() << player

        sceneManager.addScene(defaultScene, true)

        gameEngine.start()
    }
}

def game = new GameEntryPoint()
game.runGame()
