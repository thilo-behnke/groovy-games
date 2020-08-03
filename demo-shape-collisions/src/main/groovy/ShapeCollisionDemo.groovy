import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.CircleGameObject
import org.tb.gg.gameObject.component.CircleRenderComponent
import org.tb.gg.gameObject.component.MovableCircleAction
import org.tb.gg.gameObject.component.MovableCircleInputComponent
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.physics.ShapePhysicsComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.factory.KeyBoundGameObjectBuilder
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.InteractiveShape
import org.tb.gg.gameObject.shape.ShapeComposite
import org.tb.gg.global.geom.Vector

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def circle1 = (CircleGameObject) new KeyBoundGameObjectBuilder<CircleGameObject>(CircleGameObject.class)
        .setRenderComponent(new CircleRenderComponent())
        .setActions(
                (HashSet<String>) MovableCircleAction.values()*.name()
        )
        .setPhysicsComponent(new ShapePhysicsComponent(new ShapeBody(
                InteractiveShape.of(new Circle(center: Vector.unitVector() * 200.0, radius: 100.0))
        )))
        .setInputComponentClass(MovableCircleInputComponent.class)
        .setDefaultKeyMapping(MovableCircleAction.values().collectEntries { it.keys.collectEntries() { key -> [(key): it.name()] } })
        .build()

circle2 = (CircleGameObject) new GameObjectBuilder<CircleGameObject>(CircleGameObject.class)
        .setRenderComponent(new CircleRenderComponent())
        .setPhysicsComponent(new ShapePhysicsComponent(new ShapeBody(
                new Circle(center: Vector.unitVector() * 400.0, radius: 100.0)
        )))
        .setInputComponent(new NoopInputComponent())
        .build()

def defaultScene = new DefaultGameScene('default')

defaultScene.accessGameObjectProvider() << circle1
defaultScene.accessGameObjectProvider() << circle2

gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
