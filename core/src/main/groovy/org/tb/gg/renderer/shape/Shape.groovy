package org.tb.gg.renderer.shape

import io.reactivex.rxjava3.disposables.Disposable
import org.tb.gg.di.Inject
import org.tb.gg.global.geom.Vector
import org.tb.gg.global.lifecycle.Lifecycle
import org.tb.gg.input.mouseEvent.MouseEventProvider
import org.tb.gg.renderer.renderObjects.Renderable

// TODO: It would be great to model mouse interaction with a trait, but traits don't allow ast transformations.
abstract class Shape implements Renderable, Lifecycle {

    @Inject
    private MouseEventProvider mouseEventProvider

//    private Disposable mouseClicksDisposable
    private Disposable mouseMoveDisposable
    private boolean isMouseInShape

    @Override
    void onInit() {
//        mouseClicksDisposable = mouseEventProvider.mouseClicks
//                .subscribe{}
        mouseMoveDisposable = mouseEventProvider.mousePosition
            .subscribe{
                isMouseInShape = isPointWithin(it.pos)
            }
    }

    @Override
    void onDestroy() {
        mouseMoveDisposable.dispose()
    }

    abstract boolean isPointWithin(Vector pos);
//    boolean isShapeWithin(Shape shape);
//    boolean doesOverlapWith(Shape shape);
}