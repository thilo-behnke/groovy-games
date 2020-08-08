package org.tb.gg.input.mouseEvent

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.di.definition.Singleton

// TODO: Integrate into service instantiation.
interface MouseEventProvider extends Singleton {
    Observable<MouseEvent> getMouseDown()
    Observable<MouseEvent> getMouseUp()
    Observable<MouseEvent> getMouseClicks()
    Observable<MouseEvent> getMousePosition()

    MouseEvent getCurrentMousePosition()
}