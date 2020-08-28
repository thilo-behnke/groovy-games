import org.tb.gg.di.ServiceProvider
import org.tb.gg.engine.GameScene
import org.tb.gg.engine.Game
import org.tb.gg.gameObject.component.player.PlayerGameObject
import org.tb.gg.gameObject.component.score.ScoreGameObject
import org.tb.gg.resources.ResourceLoader
import org.tb.gg.resources.ShooterGameResource

import java.awt.image.BufferedImage

class GameEntryPoint implements Game {

    void runGame() {
        def defaultScene = new GameScene('default')

        def player = PlayerGameObject.create()
        defaultScene.accessGameObjectProvider() << player
        def score = ScoreGameObject.create()
        defaultScene.accessGameObjectProvider() << score

        sceneManager.addScene(defaultScene, true)

        ResourceLoader resourceLoader = (ResourceLoader) ServiceProvider.getSingletonService(ResourceLoader.class.simpleName)
        resourceLoader.loadResource('spaceship-blue.png', ShooterGameResource.SPACESHIP_BLUE.name())
        resourceLoader.loadResource('projectile-blue.png', ShooterGameResource.PROJECTILE_BLUE.name())

        gameEngine.start()
    }
}

def game = new GameEntryPoint()
game.runGame()
