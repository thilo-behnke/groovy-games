package org.tb.gg.input.keyEvent

import org.tb.gg.input.Key
import io.reactivex.rxjava3.core.Observable

interface KeyEventSubject {
    Observable<Set<Key>> pressedKeys()

    void register()
    void unregister()

    void listenToKeys(Key ...keys)
    void stopListeningToKeys(Key ...keys)
}
