import engine.DefaultGameScene
import engine.GameEngine
import gameObject.GameObjectProvider
import gameObject.player.Clock

GameEngine gameEngine = GameEngineProvider.provideGameEngine()

def gameObjectProvider = new GameObjectProvider()

def clock = Clock.create()

gameObjectProvider << clock
def defaultScene = new DefaultGameScene('default', gameObjectProvider)
gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
