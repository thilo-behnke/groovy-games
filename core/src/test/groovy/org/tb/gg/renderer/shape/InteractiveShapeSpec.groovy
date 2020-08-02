package org.tb.gg.renderer.shape

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import org.tb.gg.di.ServiceProvider
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class InteractiveShapeSpec extends Specification {

    InteractiveShape interactiveShape
    MouseEventProvider mouseEventProvider
    Shape dummyShape

    TestObserver<MouseEvent> mouseClickTestObserver

    Subject<MouseEvent> mouseClickSubject
    Subject<MouseEvent> mousePositionSubject

    void setup() {
        dummyShape = Mock(Shape)

        mouseEventProvider = Mock(MouseEventProvider)
        mouseClickSubject = BehaviorSubject.create()
        mousePositionSubject = BehaviorSubject.create()
        mouseEventProvider.mouseClicks >> mouseClickSubject
        mouseEventProvider.mousePosition >> mousePositionSubject

        ServiceProvider.setService(mouseEventProvider, 'MouseEventProvider')

        interactiveShape = InteractiveShape.of(dummyShape)
        interactiveShape.onInit()

        mouseClickTestObserver = interactiveShape.mouseClicks.test()
    }

    void cleanup() {
        mouseClickTestObserver.dispose()
        mouseClickSubject.onComplete()
        mousePositionSubject.onComplete()
        // TODO: It would be great if this could be integrated into Spock.
        ServiceProvider.reset()
    }

    // Mouse clicks.

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

    // Mouse position.

    def 'no mouse positions sent by provider, mouse is not within shape'() {
        expect:
        !interactiveShape.isMouseInShape
    }

    def 'single mouse position sent by provider, but not within shape'() {
        given:
        def event = mockMouseEvent(new Vector(x: 10, y: 22), false)
        when:
        sendMousePosition(event)
        then:
        !interactiveShape.isMouseInShape
    }

    def 'single mouse position sent by provider, point is within shape'() {
        given:
        def event = mockMouseEvent(new Vector(x: 10, y: 22), true)
        when:
        sendMousePosition(event)
        then:
        interactiveShape.isMouseInShape
    }

    def 'multiple mouse positions sent by provider, only last event counts here'() {
        given:
        def event1 = mockMouseEvent(new Vector(x: 10, y: 22), true)
        def event2 = mockMouseEvent(new Vector(x: 77, y: 22), false)
        def event3 = mockMouseEvent(new Vector(x: 77, y: 88), true)
        when:
        sendMousePosition(event1, event2, event3)
        then:
        interactiveShape.isMouseInShape
    }

    private mockMouseEvent(Vector pos, boolean isPointWithinShape) {
        dummyShape.isPointWithin(pos) >> isPointWithinShape
        new MouseEvent(pos: pos)
    }

    private sendMouseClicks(MouseEvent ...events) {
        events.each {
            mouseClickSubject.onNext(it)
        }
    }

    private sendMousePosition(MouseEvent ...events) {
        events.each {
            mousePositionSubject.onNext(it)
        }
    }
}
