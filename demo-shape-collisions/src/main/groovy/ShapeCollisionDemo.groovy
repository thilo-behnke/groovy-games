import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.DefaultGameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.MovableGameObject
import org.tb.gg.gameObject.component.CollisionVisualizationRenderComponent
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.physics.ShapePhysicsComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.global.geom.Vector

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def circle1 = (MovableGameObject) new GameObjectBuilder<MovableGameObject>(MovableGameObject.class)
        .setBody(new ShapeBody(
                new Circle(center: Vector.unitVector() * 200.0, radius: 100.0)
        ))
        .setPhysicsComponent(new ShapePhysicsComponent())
        .setRenderComponent(new CollisionVisualizationRenderComponent())
        .setInputComponent(NoopInputComponent.get())
        .build()

def circle2 = (MovableGameObject) new GameObjectBuilder<MovableGameObject>(MovableGameObject.class)
        .setBody(new ShapeBody(
                new Circle(center: Vector.unitVector() * 400.0, radius: 100.0)
        ))
        .setPhysicsComponent(new ShapePhysicsComponent())
        .setRenderComponent(new CollisionVisualizationRenderComponent())
        .setInputComponent(NoopInputComponent.get())
        .build()

def line1 = new GameObjectBuilder(MovableGameObject.class)
        .setBody(new ShapeBody(
                new Line(Vector.unitVector() * 300.0, Vector.unitVector() * 350.0)
        ))
        .setPhysicsComponent(new ShapePhysicsComponent())
        .setRenderComponent(new CollisionVisualizationRenderComponent())
        .setInputComponent(NoopInputComponent.get())
        .build()

def line2 = new GameObjectBuilder(GameObject.class)
        .setBody(new ShapeBody(
                new Line(Vector.unitVector() * 500.0, Vector.unitVector() * 650.0)
        ))
        .setPhysicsComponent(new ShapePhysicsComponent())
        .setRenderComponent(new CollisionVisualizationRenderComponent())
        .setInputComponent(NoopInputComponent.get())
        .build()

def defaultScene = new DefaultGameScene('default')

defaultScene.accessGameObjectProvider() << circle1
defaultScene.accessGameObjectProvider() << circle2
defaultScene.accessGameObjectProvider() << line1
defaultScene.accessGameObjectProvider() << line2

gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
