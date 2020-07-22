import engine.DefaultGameScene
import engine.GameEngine
import gameObject.GameObjectProvider
import input.keyEvent.KeyEventJwtAdapter

GameEngine gameEngine = GameEngineProvider.provideGameEngine()

def gameObjectProvider = new GameObjectProvider()

def defaultScene = new DefaultGameScene('default', gameObjectProvider)
gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
