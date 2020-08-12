import org.tb.gg.GameEngineProvider
import org.tb.gg.engine.GameScene
import org.tb.gg.engine.GameEngine
import org.tb.gg.gameObject.MovableGameObject
import org.tb.gg.gameObject.component.CollisionVisualizationRenderComponent
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.NoopPhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Text
import org.tb.gg.global.geom.Vector

GameEngine gameEngine = new GameEngineProvider().provideGameEngine()

def getBaseBuilder = {
        new GameObjectBuilder<MovableGameObject>(MovableGameObject.class)
                .setPhysicsComponent(new NoopPhysicsComponent())
                .setRenderComponent(new CollisionVisualizationRenderComponent())
                .setInputComponent(NoopInputComponent.get())
}

def circle1 = getBaseBuilder()
        .setBody(new ShapeBody(
                new Circle(center: Vector.unitVector() * 200.0, radius: 100.0)
        ))
        .build()

def circle2 = getBaseBuilder()
        .setBody(new ShapeBody(
                new Circle(center: Vector.unitVector() * 400.0, radius: 100.0)
        ))
        .build()

def line1 = getBaseBuilder()
        .setBody(new ShapeBody(
                new Line(Vector.unitVector() * 300.0, Vector.unitVector() * 350.0)
        ))
        .build()

def line2 = getBaseBuilder()
        .setBody(new ShapeBody(
                new Line(Vector.unitVector() * 500.0, Vector.unitVector() * 650.0)
        ))
        .build()

def rect1 = getBaseBuilder()
        .setBody(new ShapeBody(
                new Rect(Vector.unitVector() * 100.0, Vector.unitVector() * 340.0)
        ))
        .build()

def rect2 = getBaseBuilder()
        .setBody(new ShapeBody(
                new Rect(Vector.unitVector() * 400.0, Vector.unitVector() * 220.0)
        ))
        .build()

def text1 = getBaseBuilder()
        .setBody(new ShapeBody(
                new Text(
                        Vector.unitVector() * 450.0,
                        Vector.unitVector() * 100.0,
                        "hello"
                )
        ))
        .build()

def defaultScene = new GameScene('default')

defaultScene.accessGameObjectProvider() << circle1
defaultScene.accessGameObjectProvider() << circle2
defaultScene.accessGameObjectProvider() << line1
defaultScene.accessGameObjectProvider() << line2
defaultScene.accessGameObjectProvider() << rect1
defaultScene.accessGameObjectProvider() << rect2
//defaultScene.accessGameObjectProvider() << text1

gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
