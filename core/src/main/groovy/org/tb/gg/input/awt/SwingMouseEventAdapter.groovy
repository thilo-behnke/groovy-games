package org.tb.gg.input.awt

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider

import javax.swing.JFrame
import java.awt.event.MouseListener

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
            parent.alertMouseMove(e)
        }

        @Override
        void mouseExited(java.awt.event.MouseEvent e) {
            // Ignore on purpose.
        }
    }

    @Inject
    private EnvironmentService environmentService

    private BehaviorSubject<MouseEvent> mouseMoveSubject
    private BehaviorSubject<MouseEvent> mouseDownSubject
    private BehaviorSubject<MouseEvent> mouseUpSubject
    private BehaviorSubject<MouseEvent> mouseClickSubject

    private JFrame jFrame
    private MouseListener mouseListener

    SwingMouseEventAdapter() {
        mouseMoveSubject = BehaviorSubject.create()
        mouseDownSubject = BehaviorSubject.create()
        mouseUpSubject = BehaviorSubject.create()
        mouseClickSubject = BehaviorSubject.create()

        jFrame = (JFrame) environmentService.environment.environmentFrame
        mouseListener = new SwingMouseListener(this)
    }

    @Override
    void init() {
        jFrame.addMouseListener(mouseListener)
    }

    @Override
    void destroy() {
        jFrame.removeMouseListener(mouseListener)
    }

    protected alertMouseMove(java.awt.event.MouseEvent e) {
        mouseMoveSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: e.y)))
    }

    protected alertMouseDown(java.awt.event.MouseEvent e) {
        mouseDownSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: e.y)))
    }

    protected alertMouseUp(java.awt.event.MouseEvent e) {
        mouseUpSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: e.y)))
    }

    protected alertMouseClick(java.awt.event.MouseEvent e) {
        mouseClickSubject.onNext(new MouseEvent(pos: new Vector(x: e.x, y: e.y)))
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
        return mouseMoveSubject
    }
}
