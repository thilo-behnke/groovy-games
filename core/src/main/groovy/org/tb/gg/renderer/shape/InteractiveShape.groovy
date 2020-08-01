package org.tb.gg.renderer.shape

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Service
import org.tb.gg.global.lifecycle.Lifecycle
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider

class InteractiveShape<S extends Shape> implements Shape, Service {

    @Inject
    private MouseEventProvider mouseEventProvider

    @Delegate
    private final S shape

    private Disposable mouseMoveDisposable
    boolean isMouseInShape

    InteractiveShape(S shape) {
        this.shape = shape
    }

    static of(Shape shape) {
        def interactiveShape = new InteractiveShape(shape)
        return interactiveShape
    }

    S getShape() {
        return shape
    }

    Observable<MouseEvent> getMouseClicks() {
        mouseEventProvider.mouseClicks
                .filter {
                    isPointWithin(it.pos)
                }
    }

    @Override
    void init() {
        mouseMoveDisposable = mouseEventProvider.mousePosition
                .subscribe {
                    isMouseInShape = isPointWithin(it.pos)
                }
    }

    @Override
    void destroy() {
        mouseMoveDisposable.dispose()
    }
}
