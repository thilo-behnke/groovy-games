import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.RectButton
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.shape.InteractiveGameObject
import org.tb.gg.global.geom.Vector

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def defaultScene = new DefaultGameScene('default')

def button = RectButton.create(Vector.unitVector() * 500.0, Vector.unitVector() * 200.0)

defaultScene.accessGameObjectProvider() << button

gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
