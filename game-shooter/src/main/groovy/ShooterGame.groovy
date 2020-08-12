import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.Game
import org.tb.gg.gameObject.component.PlayerGameObject

class GameEntryPoint implements Game {

    void runGame() {
        def defaultScene = new DefaultGameScene('default')
        def player = PlayerGameObject.create()
        defaultScene.accessGameObjectProvider() << player
        sceneManager.addScene(defaultScene, true)

        gameEngine.start()
    }
}

def game = new GameEntryPoint()
game.runGame()
