package org.tb.gg.renderer.shape

import io.reactivex.rxjava3.disposables.Disposable
import org.tb.gg.di.Inject
import org.tb.gg.global.lifecycle.Lifecycle
import org.tb.gg.input.mouseEvent.MouseEventProvider

class InteractiveShape implements Shape, Lifecycle {

    @Inject
    private MouseEventProvider mouseEventProvider

    @Delegate
    private final Shape shape

    private Disposable mouseMoveDisposable
    private boolean isMouseInShape

    InteractiveShape(Shape shape) {
        this.shape = shape
    }

    static of(Shape shape) {
        def interactiveShape = new InteractiveShape(shape)
        return interactiveShape
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
