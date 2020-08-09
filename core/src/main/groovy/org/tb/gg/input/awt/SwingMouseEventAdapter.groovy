package org.tb.gg.input.awt

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider
import org.tb.gg.input.mouseEvent.MouseRectangleEvent

import javax.swing.JFrame
import javax.swing.JPanel
import java.awt.event.MouseListener
import java.util.concurrent.TimeUnit

class SwingMouseEventAdapter implements MouseEventProvider {
    class SwingMouseListener implements MouseListener {

        private SwingMouseEventAdapter parent

        SwingMouseListener(SwingMouseEventAdapter swingMouseEventAdapter) {
            this.parent = swingMouseEventAdapter
        }

        @Override
        void mouseClicked(java.awt.event.MouseEvent e) {
            parent.alertMouseClick(e)
        }

        @Override
        void mousePressed(java.awt.event.MouseEvent e) {
            parent.alertMouseDown(e)
        }

        @Override
        void mouseReleased(java.awt.event.MouseEvent e) {
            parent.alertMouseUp(e)
        }

        @Override
        void mouseEntered(java.awt.event.MouseEvent e) {
            // Ignore on purpose.
        }

        @Override
        void mouseExited(java.awt.event.MouseEvent e) {
            // Ignore on purpose.
        }
    }

    @Inject
    private EnvironmentService environmentService

    private BehaviorSubject<MouseEvent> mouseDownSubject
    private BehaviorSubject<MouseEvent> mouseUpSubject
    private BehaviorSubject<MouseEvent> mouseClickSubject
    private PublishSubject<MouseRectangleEvent> mouseRectanglesSubject
    private Observable<MouseEvent> mouseMoveEvent
    private Disposable mouseMoveEventDisposable

    private MouseEvent currentMousePosition

    private JFrame jFrame
    private JPanel jPanel
    private MouseListener mouseListener

    @Override
    void init() {
        mouseDownSubject = BehaviorSubject.create()
        mouseUpSubject = BehaviorSubject.create()
        mouseClickSubject = BehaviorSubject.create()
        mouseRectanglesSubject = PublishSubject.create()

        jFrame = (JFrame) environmentService.environment.environmentFrame
        jPanel = (JPanel) environmentService.environment.renderDestination

        subscribeAndProcessMouseMoveEvents()
        subscribeAndProcessRectangleEvents()

        mouseListener = new SwingMouseListener(this)
        jFrame.addMouseListener(mouseListener)

        // This class needs to provide the current position of the mouse synchronously, therefore subscribe here to make the observable hot.
        mouseMoveEventDisposable = mouseMoveEvent.subscribe()
    }

    private subscribeAndProcessMouseMoveEvents() {
        mouseMoveEvent = (Observable<MouseEvent>) Observable
                .interval((1000 / 60).toInteger(), TimeUnit.MILLISECONDS)
                .map {
                    Optional.ofNullable(jPanel.getMousePosition())
                }
                .filter {
                    it.isPresent()
                }
                .map { mousePosOpt ->
                    def mousePos = mousePosOpt.get()
                    return new MouseEvent(pos: new Vector(x: mousePos.x, y: jPanel.getHeight() - mousePos.y))
                }
                .doOnNext { mouseEvent ->
                    currentMousePosition = mouseEvent
                }
    }

    // TODO: This does not have to be in the swing adapter class, but could be a processing class on top.
    private subscribeAndProcessRectangleEvents() {
        mouseDown.switchMap { start ->
            System.println(start)
            mouseUp.take(1).map { stop ->
                System.println(stop)
                new MouseRectangleEvent(rect: new Rect(start.pos, stop.pos - start.pos))
            }
        }.doOnNext { MouseRectangleEvent event ->
            mouseRectanglesSubject.onNext(event)
        }.subscribe()
    }

    @Override
    void destroy() {
        jFrame.removeMouseListener(mouseListener)
        mouseMoveEventDisposable.dispose()
    }

    protected alertMouseDown(java.awt.event.MouseEvent e) {
        mouseDownSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: jFrame.getHeight() - e.y)))
    }

    protected alertMouseUp(java.awt.event.MouseEvent e) {
        mouseUpSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: jFrame.getHeight() - e.y)))
    }

    protected alertMouseClick(java.awt.event.MouseEvent e) {
        mouseClickSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: jFrame.getHeight() - e.y)))
    }

    @Override
    Observable<MouseEvent> getMouseDown() {
        return mouseDownSubject
    }

    @Override
    Observable<MouseEvent> getMouseUp() {
        return mouseUpSubject
    }

    @Override
    Observable<MouseEvent> getMouseClicks() {
        return mouseClickSubject
    }

    @Override
    Observable<MouseEvent> getMousePosition() {
        return mouseMoveEvent
    }

    @Override
    Observable<MouseRectangleEvent> getMouseRectangles() {
        return mouseRectanglesSubject
    }

    @Override
    MouseEvent getCurrentMousePosition() {
        return currentMousePosition
    }
}
