package org.tb.gg.gameObject

import io.reactivex.rxjava3.disposables.Disposable
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.component.MovableCircleAction
import org.tb.gg.gameObject.component.MovableShapeInputComponent
import org.tb.gg.gameObject.services.InputComponentProvider
import org.tb.gg.gameObject.traits.InteractiveBody
import org.tb.gg.global.geom.Vector

class MovableGameObject extends GameObject implements InteractiveBody {

    @Inject
    private InputComponentProvider inputComponentProvider
    private Disposable mouseClickDisposable

    @Override
    void update(Long timestamp, Long delta) {
        handleMovement(timestamp, delta)
    }

    @Override
    void init() {
        super.init()
        mouseClickDisposable = getMouseClicks().subscribe {
            inputComponentProvider.setAssignedMovableGameObject(this)
        }
    }

    @Override
    void destroy() {
        super.destroy()
        mouseClickDisposable.dispose()
    }

    private handleMovement(Long timestamp, Long delta) {
        def activeActions = inputComponent.getActiveActions().collect { MovableCircleAction.valueOf(it) }
        if (activeActions.isEmpty()) {
            return
        }
        def shape = body.getStructure()
        def center = shape.center
        def newX = center.x
        def newY = center.y
        // Update X.
        if (activeActions.contains(MovableCircleAction.RIGHT)) {
            newX = newX + 1 * delta
        } else if (activeActions.contains(MovableCircleAction.LEFT)) {
            newX = newX - 1 * delta
        }
        // Update Y.
        if (activeActions.contains(MovableCircleAction.UP)) {
            newY = newY + 1 * delta
        } else if (activeActions.contains(MovableCircleAction.DOWN)) {
            newY = newY - 1 * delta
        }
        shape.center = new Vector(x: newX, y: newY)
    }
}
