package org.tb.gg.input.actions

import io.reactivex.rxjava3.core.Observable

interface InputActionProvider {
    Observable<Set<String>> activeActions$()
    Set<String> activeActions()
}
