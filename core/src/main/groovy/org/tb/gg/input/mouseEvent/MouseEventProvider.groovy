package org.tb.gg.input.mouseEvent

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.di.definition.Singleton

interface MouseEventProvider extends Singleton {
    Observable<MouseEvent> getMouseDown()
    Observable<MouseEvent> getMouseUp()
    Observable<MouseEvent> getMouseClicks()
    Observable<MouseEvent> getMousePosition()
    Observable<MouseRectangleEvent> getMouseRectangles()

    MouseEvent getCurrentMousePosition()
}