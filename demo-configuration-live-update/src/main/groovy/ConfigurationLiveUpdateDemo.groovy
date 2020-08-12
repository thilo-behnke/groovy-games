import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.Game
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.traits.InteractiveBody
import org.tb.gg.gameObject.RectButton
import org.tb.gg.global.geom.Vector

class ConfigurationLiveUpdateDemoEntrypoint implements Game {

    @Override
    void runGame() {
        def defaultScene = new DefaultGameScene('default')

        def button = RectButton.create(Vector.unitVector() * 500.0, Vector.unitVector() * 200.0)
        defaultScene.accessGameObjectProvider() << button
        addScene(defaultScene, true)

        gameEngine.start()
    }
}

def game = new ConfigurationLiveUpdateDemoEntrypoint()
game.runGame()
