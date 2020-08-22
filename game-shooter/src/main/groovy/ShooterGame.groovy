

import org.tb.gg.engine.GameScene
import org.tb.gg.engine.Game
import org.tb.gg.gameObject.component.player.PlayerGameObject
import org.tb.gg.gameObject.component.score.ScoreGameObject

class GameEntryPoint implements Game {

    void runGame() {
        def defaultScene = new GameScene('default')

        def player = PlayerGameObject.create()
        defaultScene.accessGameObjectProvider() << player
        def score = ScoreGameObject.create()
        defaultScene.accessGameObjectProvider() << score

        sceneManager.addScene(defaultScene, true)

        gameEngine.start()
    }
}

def game = new GameEntryPoint()
game.runGame()
