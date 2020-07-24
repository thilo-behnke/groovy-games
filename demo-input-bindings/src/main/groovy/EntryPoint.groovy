import engine.DefaultGameScene
import engine.GameEngine
import gameObject.GameObjectProvider

GameEngine gameEngine = GameEngineProvider.provideGameEngine()

def gameObjectProvider = new GameObjectProvider()

def defaultScene = new DefaultGameScene('default', gameObjectProvider)
gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
