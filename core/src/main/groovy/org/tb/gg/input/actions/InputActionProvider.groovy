package org.tb.gg.input.actions

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.gameObject.lifecycle.Lifecycle

interface InputActionProvider extends Lifecycle {
    Observable<Set<String>> activeActions$()
    Set<String> activeActions()
}
