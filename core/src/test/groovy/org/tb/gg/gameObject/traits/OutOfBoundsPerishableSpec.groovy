package org.tb.gg.gameObject.traits

import org.tb.gg.di.ServiceProvider
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.PerishAfterTTL
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.NoopPhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.render.NoopRenderComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import org.tb.gg.world.WorldState
import org.tb.gg.world.WorldStateProvider
import spock.lang.Specification

class OutOfBoundsPerishableSpec extends Specification {

    BaseGameObject gameObject
    WorldStateProvider worldStateProvider
    WorldState initialWorldState

    def setup() {
        initializeServices()
        initializeGameObject()
    }

    def cleanup() {
        ServiceProvider.reset()
    }

    def 'game object is in bounds'() {
        when:
        gameObject.setBody(new ShapeBody(new Point(pos: new Vector(x: 103, y: 91))))
        then:
        !gameObject.shouldPerish()
    }

    def 'game object is out of bounds'() {
        when:
        gameObject.setBody(new ShapeBody(new Point(pos: new Vector(x: 200, y: 101))))
        then:
        gameObject.shouldPerish()
    }

    private initializeServices() {
        initialWorldState = new WorldState(bounds: new Rect(new Vector(x: 100, y: 100), new Vector(x: 10, y: 10)))
        worldStateProvider = Mock(WorldStateProvider)
        ServiceProvider.setService(worldStateProvider, 'WorldStateProvider')
        worldStateProvider.get() >> initialWorldState
    }

    private initializeGameObject() {
        gameObject = new GameObjectBuilder(OutOfBoundsGameObject)
                .setPhysicsComponent(new NoopPhysicsComponent())
                .setInputComponent(NoopInputComponent.get())
                .setRenderComponent(NoopRenderComponent.get())
                .setBody(new ShapeBody(new Point(pos: Vector.unitVector())))
                .build()
    }
}

class OutOfBoundsGameObject extends BaseGameObject implements OutOfBoundsPerishable {}
