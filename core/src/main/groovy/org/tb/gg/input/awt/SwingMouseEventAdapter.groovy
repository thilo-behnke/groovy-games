package org.tb.gg.input.awt

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.tb.gg.di.Inject
import org.tb.gg.env.frame.GraphicsAPIFrameProvider
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider
import org.tb.gg.input.mouseEvent.MouseRectangleEvent
import org.tb.gg.renderer.destination.JPanelDestination
import org.tb.gg.renderer.destination.RenderDestination

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
    private GraphicsAPIFrameProvider frameService
    @Inject
    private RenderDestination renderDestination

    private PublishSubject<MouseEvent> mouseDownSubject
    private PublishSubject<MouseEvent> mouseUpSubject
    private PublishSubject<MouseEvent> mouseClickSubject
    private PublishSubject<MouseRectangleEvent> mouseRectanglesSubject
    private Observable<MouseEvent> mouseMoveEvent
    private Observable<MouseRectangleEvent> mouseRectangleEvent
    private Disposable mouseMoveEventDisposable
    private Disposable mouseRectangleDisposable

    private MouseEvent currentMousePosition

    private MouseListener mouseListener

    @Override
    void init() {
        mouseDownSubject = PublishSubject.create()
        mouseUpSubject = PublishSubject.create()
        mouseClickSubject = PublishSubject.create()
        mouseRectanglesSubject = PublishSubject.create()

        subscribeAndProcessMouseMoveEvents()
        subscribeAndProcessRectangleEvents()

        mouseListener = new SwingMouseListener(this)
        getJFrame().addMouseListener(mouseListener)

        // This class needs to provide the current position of the mouse synchronously, therefore subscribe here to make the observable hot.
        mouseMoveEventDisposable = mouseMoveEvent.subscribe()
        mouseRectangleDisposable = mouseRectangleEvent.subscribe()
    }

    private JFrame getJFrame() {
        (JFrame) frameService.getFrame()
    }

    private JPanel getJPanel() {
        ((JPanelDestination) renderDestination).jPanelWrapper
    }

    private subscribeAndProcessMouseMoveEvents() {
        mouseMoveEvent = (Observable<MouseEvent>) Observable
                .interval((1000 / 60).toInteger(), TimeUnit.MILLISECONDS)
                .map {
                    Optional.ofNullable(getJPanel().getMousePosition())
                }
                .filter {
                    it.isPresent()
                }
                .map { mousePosOpt ->
                    def mousePos = mousePosOpt.get()
                    return new MouseEvent(pos: new Vector(x: mousePos.x, y: getJPanel().getHeight() - mousePos.y))
                }
                .doOnNext { mouseEvent ->
                    currentMousePosition = mouseEvent
                }
    }

    // TODO: This does not have to be in the swing adapter class, but could be a processing class on top.
    private subscribeAndProcessRectangleEvents() {
        mouseRectangleEvent = mouseDown.switchMap { start ->
            mouseUp.take(1).map { stop ->
                new MouseRectangleEvent(rect: new Rect(start.pos, stop.pos - start.pos))
            }.filter { it.rect.size > 0 }
        }.doOnNext { MouseRectangleEvent event ->
            mouseRectanglesSubject.onNext(event)
        }
    }

    @Override
    void destroy() {
        getJFrame().removeMouseListener(mouseListener)
        mouseMoveEventDisposable.dispose()
        mouseRectangleDisposable.dispose()
    }

    protected alertMouseDown(java.awt.event.MouseEvent e) {
        mouseDownSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: getJFrame().getHeight() - e.y)))
    }

    protected alertMouseUp(java.awt.event.MouseEvent e) {
        mouseUpSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: getJFrame().getHeight() - e.y)))
    }

    protected alertMouseClick(java.awt.event.MouseEvent e) {
        mouseClickSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: getJFrame().getHeight() - e.y)))
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
