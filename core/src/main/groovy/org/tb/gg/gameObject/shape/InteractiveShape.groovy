package org.tb.gg.gameObject.shape

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider

class InteractiveShape<S extends Shape> implements Shape, Lifecycle {

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
    void onInit() {
        mouseMoveDisposable = mouseEventProvider.mousePosition
                .subscribe {
                    isMouseInShape = isPointWithin(it.pos)
                }
    }

    @Override
    void onDestroy() {
        mouseMoveDisposable.dispose()
    }
}
