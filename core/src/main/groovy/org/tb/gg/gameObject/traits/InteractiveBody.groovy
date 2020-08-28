package org.tb.gg.gameObject.traits

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.components.body.Body
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.input.mouseEvent.MouseEvent
import org.tb.gg.input.mouseEvent.MouseEventProvider
import org.tb.gg.input.mouseEvent.MouseRectangleEvent

trait InteractiveBody {
    @Inject
    private MouseEventProvider mouseEventProvider

    abstract Body getBody()

    Observable<MouseEvent> getMouseClicks() {
        mouseEventProvider.mouseClicks
                .filter {
                    body.shape.isPointWithin(it.pos)
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
        body.shape.isPointWithin(mouseEventProvider.getCurrentMousePosition().pos)
    }
}