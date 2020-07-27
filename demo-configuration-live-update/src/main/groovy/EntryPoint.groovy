import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.GameObjectProvider

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def gameObjectProvider = new GameObjectProvider()
def defaultScene = new DefaultGameScene('default', gameObjectProvider)
