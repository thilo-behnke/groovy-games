import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.CircleGameObject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.component.CircleRenderComponent
import org.tb.gg.gameObject.component.MovableCircleAction
import org.tb.gg.gameObject.component.MovableCircleInputComponent
import org.tb.gg.gameObject.components.NoopInputComponent
import org.tb.gg.gameObject.components.RenderComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.factory.KeyBoundGameObjectBuilder
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.Key
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.shape.Circle

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()
def gameObjectProvider = new GameObjectProvider()

def circle1 = (CircleGameObject) new KeyBoundGameObjectBuilder<CircleGameObject>(CircleGameObject.class)
        .setRenderComponent(new CircleRenderComponent())
        .setActions(
                (HashSet<String>) MovableCircleAction.values()*.name()
        )
        .setInputComponentClass(MovableCircleInputComponent.class)
        .setDefaultKeyMapping(MovableCircleAction.values().collectEntries{[(it.key): it.name()]})
        .build()
circle1.center = Vector.unitVector() * 200.0
circle1.radius = 100.0

def circle2 = (CircleGameObject) new GameObjectBuilder<CircleGameObject>(CircleGameObject.class)
        .setRenderComponent(new CircleRenderComponent())
        .setInputComponent(new NoopInputComponent())
        .build()
circle2.center = Vector.unitVector() * 400.0
circle2.radius = 100.0

gameObjectProvider << circle1
gameObjectProvider << circle2

def defaultScene = new DefaultGameScene('default', gameObjectProvider)
gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
