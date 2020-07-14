import engine.DefaultGameScene
import engine.GameEngine
import gameObject.GameObject
import gameObject.GameObjectProvider
import gameObject.components.RenderComponent
import gameObject.player.Player
import gameObject.player.PlayerRenderComponent
import global.geom.Vector
import renderer.renderObjects.RenderNode
import renderer.shape.Line

GameEngine gameEngine = GameEngineProvider.provideGameEngine()

def gameObjectProvider = new GameObjectProvider()

def player = Player.create()

gameObjectProvider << player
def defaultScene = new DefaultGameScene('default', gameObjectProvider)
gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
