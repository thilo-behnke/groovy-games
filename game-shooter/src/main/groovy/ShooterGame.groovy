import org.tb.gg.di.ServiceProvider
import org.tb.gg.engine.GameScene
import org.tb.gg.engine.Game
import org.tb.gg.gameObject.component.player.PlayerGameObject
import org.tb.gg.gameObject.component.score.ScoreGameObject
import org.tb.gg.resources.ResourceLoader

import java.awt.image.BufferedImage

class GameEntryPoint implements Game {

    void runGame() {
        def defaultScene = new GameScene('default')

        def player = PlayerGameObject.create()
        defaultScene.accessGameObjectProvider() << player
        def score = ScoreGameObject.create()
        defaultScene.accessGameObjectProvider() << score

        sceneManager.addScene(defaultScene, true)

        ResourceLoader resourceLoader = ServiceProvider.getSingletonService(ResourceLoader.class.simpleName)
        resourceLoader.loadResource('spaceship-blue.png', 'SPACESHIP_BLUE')
        BufferedImage image = resourceLoader.getResource('SPACESHIP_BLUE')

        gameEngine.start()
    }
}

def game = new GameEntryPoint()
game.runGame()
