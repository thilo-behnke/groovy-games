import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.Button
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.global.geom.Vector

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def gameObjectProvider = new GameObjectProvider()
def defaultScene = new DefaultGameScene('default', gameObjectProvider)

def button = Button.create(Vector.unitVector() * 500.0, Vector.unitVector() * 200.0)

gameObjectProvider << button

gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()

// TODO: 1) Click listener on canvas
// TODO: 2) Check if click is in rect (=button)
