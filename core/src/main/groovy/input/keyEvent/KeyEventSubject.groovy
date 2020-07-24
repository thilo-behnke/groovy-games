package input.keyEvent

import input.Key
import input.keyEvent.KeyEvent
import io.reactivex.rxjava3.core.Observable

interface KeyEventSubject {
    Observable<Set<Key>> pressedKeys()

    void register()
    void unregister()

    void listenToKeys(Key ...keys)
    void stopListeningToKeys(Key ...keys)
}
