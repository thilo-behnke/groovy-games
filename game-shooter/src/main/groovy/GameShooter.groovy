import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.component.PlayerGameObject

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def defaultScene = new DefaultGameScene('default')

def player = PlayerGameObject.create()

defaultScene.accessGameObjectProvider() << player

gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
