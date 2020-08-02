package org.tb.gg.renderer.shape

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import org.tb.gg.di.ServiceProvider
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.Key
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class InteractiveShapeSpec extends Specification {

    InteractiveShape interactiveShape
    MouseEventProvider mouseEventProvider
    Shape dummyShape

    TestObserver<MouseEvent> mouseEventTestObserver

    Subject<MouseEvent> mouseClickSubject

    void setup() {
        dummyShape = Mock(Shape)

        mouseEventProvider = Mock(MouseEventProvider)
        mouseClickSubject = BehaviorSubject.create()
        mouseEventProvider.mouseClicks >> mouseClickSubject
        mouseEventProvider.mousePosition >> Observable.empty()

        ServiceProvider.setService(mouseEventProvider, 'MouseEventProvider')

        interactiveShape = InteractiveShape.of(dummyShape)
        interactiveShape.onInit()

        mouseEventTestObserver = interactiveShape.mouseClicks.test()
    }

    void cleanup() {
        mouseEventTestObserver.dispose()
        mouseClickSubject.onComplete()
        // TODO: It would be great if this could be integrated into Spock.
        ServiceProvider.reset()
    }

    // Mouse clicks.

    def 'no mouse clicks sent by provider'() {
        expect:
        mouseEventTestObserver.assertValueCount(0)
    }

    def 'single mouse click sent by provider, but not within shape'() {
        given:
        def event = mockMouseEvent(new Vector(x: 10, y: 22), false)
        when:
        sendMouseClicks(event)
        then:
        mouseEventTestObserver.assertValueCount(0)
    }

    def 'single mouse click sent by provider, point is within shape'() {
        given:
        def event = mockMouseEvent(new Vector(x: 10, y: 22), true)
        when:
        sendMouseClicks(event)
        then:
        mouseEventTestObserver.assertValues(event)
    }

    def 'multiple mouse clicks sent by provider, only some points are within shape'() {
        given:
        def event1 = mockMouseEvent(new Vector(x: 10, y: 22), true)
        def event2 = mockMouseEvent(new Vector(x: 77, y: 22), false)
        def event3 = mockMouseEvent(new Vector(x: 77, y: 88), true)
        when:
        sendMouseClicks(event1, event2, event3)
        then:
        mouseEventTestObserver.assertValues(event1, event3)
    }

    // Mouse position.

    private mockMouseEvent(Vector pos, boolean isPointWithinShape) {
        dummyShape.isPointWithin(pos) >> isPointWithinShape
        new MouseEvent(pos: pos)
    }

    private sendMouseClicks(MouseEvent ...events) {
        events.each {
            mouseClickSubject.onNext(it)
        }
    }
}
