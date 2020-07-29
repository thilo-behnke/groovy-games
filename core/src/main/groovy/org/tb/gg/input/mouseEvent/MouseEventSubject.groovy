package org.tb.gg.input.mouseEvent

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.global.geom.Vector

interface MouseEventSubject {
    Observable<MouseEvent> getMouseDown()
    Observable<MouseEvent> getMouseUp()
    Observable<MouseEvent> getMouseClicks()


    // TODO: Move this into init / destroy methods of service injection. Requires multiple instances of the same service.
    void register()
    void unregister()
}