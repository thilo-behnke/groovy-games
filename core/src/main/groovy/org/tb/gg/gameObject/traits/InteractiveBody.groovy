package org.tb.gg.gameObject.traits

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider

trait InteractiveBody implements Lifecycle {
    @Inject
    private MouseEventProvider mouseEventProvider

    private Disposable mouseMoveDisposable
    boolean isMouseInShape

    abstract ShapeBody getBody()

    Observable<MouseEvent> getMouseClicks() {
        mouseEventProvider.mouseClicks
                .filter {
                    body.isPointWithin(it.pos)
                }
    }

    @Override
    void onInit() {
        mouseMoveDisposable = mouseEventProvider.mousePosition
                .subscribe {
                    isMouseInShape = body.isPointWithin(it.pos)
                }
    }

    @Override
    void onDestroy() {
        mouseMoveDisposable.dispose()
    }
}