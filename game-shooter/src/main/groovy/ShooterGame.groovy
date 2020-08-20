

import org.tb.gg.engine.GameScene
import org.tb.gg.engine.Game
import org.tb.gg.gameObject.component.enemies.RegularEnemyGameObject
import org.tb.gg.gameObject.component.player.PlayerGameObject
import org.tb.gg.global.geom.Vector

class GameEntryPoint implements Game {

    void runGame() {
        def defaultScene = new GameScene('default')
        def player = PlayerGameObject.create()
        defaultScene.accessGameObjectProvider() << player
        sceneManager.addScene(defaultScene, true)

        Thread.start { gameEngine.start() }

        def enemy = RegularEnemyGameObject.create(new Vector(x: 200, y: 200))
        defaultScene.accessGameObjectProvider() << enemy
    }
}

def game = new GameEntryPoint()
game.runGame()
