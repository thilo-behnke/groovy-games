package org.tb.gg.gameObject.traits

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider
import org.tb.gg.input.mouseEvent.MouseRectangleEvent

trait InteractiveBody {
    @Inject
    private MouseEventProvider mouseEventProvider

    abstract ShapeBody getBody()

    Observable<MouseEvent> getMouseClicks() {
        mouseEventProvider.mouseClicks
                .filter {
                    body.isPointWithin(it.pos)
                }
    }

    Observable<MouseRectangleEvent> getMouseRectangles() {
        mouseEventProvider.mouseRectangles
            .filter {
                body.collidesWith(it.rect)
            }
    }

    boolean getIsMouseInShape() {
        if (mouseEventProvider.currentMousePosition == null) {
            return false
        }
        body.isPointWithin(mouseEventProvider.getCurrentMousePosition().pos)
    }
}