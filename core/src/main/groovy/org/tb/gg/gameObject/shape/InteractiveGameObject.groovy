package org.tb.gg.gameObject.shape

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider

class InteractiveGameObject<G extends GameObject> extends GameObject implements Lifecycle {

    @Inject
    private MouseEventProvider mouseEventProvider

    private G gameObject

    private Disposable mouseMoveDisposable
    boolean isMouseInShape

    private InteractiveGameObject(G gameObject) {
        this.gameObject = gameObject
    }

    static InteractiveGameObject of(GameObject gameObject) {
        def interactiveShape = new InteractiveGameObject(gameObject)
        return interactiveShape
    }

    Observable<MouseEvent> getMouseClicks() {
        mouseEventProvider.mouseClicks
                .filter {
                    gameObject.body.isPointWithin(it.pos)
                }
    }

    @Override
    void onInit() {
        gameObject.onInit()
        mouseMoveDisposable = mouseEventProvider.mousePosition
                .subscribe {
                    isMouseInShape = gameObject.body.isPointWithin(it.pos)
                }
    }

    @Override
    void onDestroy() {
        gameObject.onDestroy()
        mouseMoveDisposable.dispose()
    }
}
