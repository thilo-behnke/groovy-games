import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.player.Clock

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def clock = Clock.create()

def defaultScene = new DefaultGameScene('default')
defaultScene.accessGameObjectProvider() << clock
gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
