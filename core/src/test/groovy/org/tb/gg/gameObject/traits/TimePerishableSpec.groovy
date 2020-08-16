package org.tb.gg.gameObject.traits

import org.tb.gg.di.ServiceProvider
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.PerishAfterTTL
import org.tb.gg.gameObject.PerishConditions
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.NoopPhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.render.NoopRenderComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.global.geom.Vector
import org.tb.gg.world.WorldState
import org.tb.gg.world.WorldStateProvider
import spock.lang.Specification

class TimePerishableSpec extends Specification {

    GameObject gameObject
    WorldStateProvider worldStateProvider
    WorldState initialWorldState

    def setup() {
        initializeServices()
        initializeGameObject()
    }

    def cleanup() {
        ServiceProvider.reset()
    }

    def 'before time runs out'() {
        expect:
        !gameObject.shouldPerish()
    }

    def 'after time runs out'() {
        when:
        initialWorldState.currentLoopTimestamp = initialWorldState.currentLoopTimestamp + 10_001L
        worldStateProvider.get() >> initialWorldState
        then:
        gameObject.shouldPerish()
    }

    private initializeServices() {
        initialWorldState = new WorldState(currentLoopTimestamp: 1597442901002)
        worldStateProvider = Mock(WorldStateProvider)
        ServiceProvider.setService(worldStateProvider, 'WorldStateProvider')
        worldStateProvider.get() >> initialWorldState
    }

    private initializeGameObject() {
        gameObject = new GameObjectBuilder(TimePerishableGameObject)
                .setPhysicsComponent(new NoopPhysicsComponent())
                .setInputComponent(NoopInputComponent.get())
                .setRenderComponent(NoopRenderComponent.get())
                .setBody(new ShapeBody(new Point(pos: Vector.unitVector())))
                .build()
    }
}

@PerishAfterTTL(10_000L)
@PerishConditions
class TimePerishableGameObject extends BaseGameObject implements TimePerishable {}

