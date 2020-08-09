package org.tb.gg.gameObject

import io.reactivex.rxjava3.disposables.Disposable
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.component.MovableGameObjectAction
import org.tb.gg.gameObject.services.InputComponentProvider
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.traits.InteractiveBody
import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants

class MovableGameObject extends GameObject implements InteractiveBody {

    @Inject
    private InputComponentProvider inputComponentProvider
    private Disposable mouseClickDisposable
    private Disposable mouseRectangleDisposable

    @Override
    void update(Long timestamp, Long delta) {
        handleMovement(timestamp, delta)
    }

    @Override
    void init() {
        super.init()
        mouseClickDisposable = mouseClicks.subscribe {
            inputComponentProvider.setAssignedMovableGameObject(this)
        }
        mouseRectangleDisposable = mouseRectangles.subscribe {
            System.println("Mouse rectangle collision: " + this)
            inputComponentProvider.setAssignedMovableGameObject(this)
        }
    }

    @Override
    void destroy() {
        super.destroy()
        mouseClickDisposable.dispose()
    }

    private handleMovement(Long timestamp, Long delta) {
        def activeActions = inputComponent.getActiveActions().collect { MovableGameObjectAction.valueOf(it) }
        if (activeActions.isEmpty()) {
            return
        }
        def shape = body.getStructure()
        def center = shape.center
        def newX = center.x
        def newY = center.y
        // Update X.
        if (activeActions.contains(MovableGameObjectAction.RIGHT)) {
            newX = newX + 1 * delta
        } else if (activeActions.contains(MovableGameObjectAction.LEFT)) {
            newX = newX - 1 * delta
        }
        // Update Y.
        if (activeActions.contains(MovableGameObjectAction.UP)) {
            newY = newY + 1 * delta
        } else if (activeActions.contains(MovableGameObjectAction.DOWN)) {
            newY = newY - 1 * delta
        }
        shape.center = new Vector(x: newX, y: newY)

        // Collision detection won't work for rects atm if they are rotated!
        if ((activeActions.contains(MovableGameObjectAction.ROTATE_COUNTER) || activeActions.contains(MovableGameObjectAction.ROTATE)) && shape instanceof Line) {
            def line = (Line) shape
            def lineDirection = line.end - line.start
            def rotationDirection = activeActions.contains(MovableGameObjectAction.ROTATE) ? -1 : 1
            def rotatedLineDirection = lineDirection.rotate(rotationDirection * MathConstants.PI / 100)
            line.end = line.start + rotatedLineDirection
        }
    }
}
