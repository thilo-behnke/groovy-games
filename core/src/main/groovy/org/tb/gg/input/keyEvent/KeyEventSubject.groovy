package org.tb.gg.input.keyEvent

import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.input.Key
import io.reactivex.rxjava3.core.Observable

interface KeyEventSubject extends Lifecycle {
    Observable<Set<Key>> pressedKeys()

    void listenToKeys(Key ...keys)
    void stopListeningToKeys(Key ...keys)
}
