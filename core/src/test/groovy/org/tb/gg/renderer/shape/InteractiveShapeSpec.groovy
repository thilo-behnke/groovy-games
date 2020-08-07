package org.tb.gg.renderer.shape

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import org.tb.gg.di.ServiceProvider
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.physics.ShapePhysicsComponent
import org.tb.gg.gameObject.components.render.NoopRenderComponent
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.InteractiveGameObject
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class InteractiveGameObjectMouseClickSpec extends Specification {

    InteractiveGameObject interactiveGameObject
    MouseEventProvider mouseEventProvider
    GameObject dummyGameObject
    ShapeBody mockBody

    TestObserver<MouseEvent> mouseClickTestObserver

    Subject<MouseEvent> mouseClickSubject

    void setup() {
        mockBody = Mock(ShapeBody)
        dummyGameObject = new GameObjectBuilder(GameObject.class)
                .setBody(mockBody)
                .setRenderComponent(NoopRenderComponent.get())
                .setInputComponent(NoopInputComponent.get())
                .build()

        mouseEventProvider = Mock(MouseEventProvider)
        mouseClickSubject = BehaviorSubject.create()
        mouseEventProvider.mouseClicks >> mouseClickSubject
        mouseEventProvider.mousePosition >> Observable.empty()

        ServiceProvider.setService(mouseEventProvider, 'MouseEventProvider')

        interactiveGameObject = InteractiveGameObject.of(dummyGameObject)
        interactiveGameObject.onInit()

        mouseClickTestObserver = interactiveGameObject.mouseClicks.test()
    }

    void cleanup() {
        mouseClickTestObserver.dispose()
        mouseClickSubject.onComplete()
        // TODO: It would be great if this could be integrated into Spock.
        ServiceProvider.reset()
    }

    def 'no mouse clicks sent by provider'() {
        expect:
        mouseClickTestObserver.assertValueCount(0)
    }

    def 'single mouse click sent by provider, but not within shape'() {
        given:
        def event = mockMouseEvent(new Vector(x: 10, y: 22), false)
        when:
        sendMouseClicks(event)
        then:
        mouseClickTestObserver.assertValueCount(0)
    }

    def 'single mouse click sent by provider, point is within shape'() {
        given:
        def event = mockMouseEvent(new Vector(x: 10, y: 22), true)
        when:
        sendMouseClicks(event)
        then:
        mouseClickTestObserver.assertValues(event)
    }

    def 'multiple mouse clicks sent by provider, only some points are within shape'() {
        given:
        def event1 = mockMouseEvent(new Vector(x: 10, y: 22), true)
        def event2 = mockMouseEvent(new Vector(x: 77, y: 22), false)
        def event3 = mockMouseEvent(new Vector(x: 77, y: 88), true)
        when:
        sendMouseClicks(event1, event2, event3)
        then:
        mouseClickTestObserver.assertValues(event1, event3)
    }

    private mockMouseEvent(Vector pos, boolean isPointWithinShape) {
        mockBody.isPointWithin(pos) >> isPointWithinShape
        new MouseEvent(pos: pos)
    }

    private sendMouseClicks(MouseEvent ...events) {
        events.each {
            mouseClickSubject.onNext(it)
        }
    }
}

@Unroll
class InteractiveGameObjectMousePositionSpec extends Specification {

    InteractiveGameObject interactiveGameObject
    MouseEventProvider mouseEventProvider
    GameObject dummyGameObject

    Subject<MouseEvent> mousePositionSubject

    void setup() {
        dummyGameObject = Mock(GameObject)

        mouseEventProvider = Mock(MouseEventProvider)
        mousePositionSubject = BehaviorSubject.create()
        mouseEventProvider.mouseClicks >> Observable.empty()
        mouseEventProvider.mousePosition >> mousePositionSubject

        ServiceProvider.setService(mouseEventProvider, 'MouseEventProvider')

        interactiveGameObject = InteractiveGameObject.of(dummyGameObject)
        interactiveGameObject.onInit()
    }

    void cleanup() {
        mousePositionSubject.onComplete()
        // TODO: It would be great if this could be integrated into Spock.
        ServiceProvider.reset()
    }

    def 'no mouse positions sent by provider, mouse is not within shape'() {
        expect:
        !interactiveGameObject.isMouseInShape
    }

    def 'single mouse position sent by provider, but not within shape'() {
        given:
        def event = mockMouseEvent(new Vector(x: 10, y: 22), false)
        when:
        sendMousePosition(event)
        then:
        !interactiveGameObject.isMouseInShape
    }

    def 'single mouse position sent by provider, point is within shape'() {
        given:
        def event = mockMouseEvent(new Vector(x: 10, y: 22), true)
        when:
        sendMousePosition(event)
        then:
        interactiveGameObject.isMouseInShape
    }

    def 'multiple mouse positions sent by provider, only last event counts here'() {
        given:
        def event1 = mockMouseEvent(new Vector(x: 10, y: 22), true)
        def event2 = mockMouseEvent(new Vector(x: 77, y: 22), false)
        def event3 = mockMouseEvent(new Vector(x: 77, y: 88), true)
        when:
        sendMousePosition(event1, event2, event3)
        then:
        interactiveGameObject.isMouseInShape
    }

    private mockMouseEvent(Vector pos, boolean isPointWithinShape) {
        dummyGameObject.body.isPointWithin(pos) >> isPointWithinShape
        new MouseEvent(pos: pos)
    }

    private sendMousePosition(MouseEvent ...events) {
        events.each {
            mousePositionSubject.onNext(it)
        }
    }
}
