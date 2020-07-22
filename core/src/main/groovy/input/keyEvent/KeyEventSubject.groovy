package input.keyEvent

import input.keyEvent.KeyEvent
import io.reactivex.rxjava3.core.Observable

interface KeyEventSubject {
    Observable<Set<KeyEvent>> pressedKeys()

    void register()
    void unregister()

    void listenToKeys(Integer ...keyCode)
    void stopListeningToKeys(Integer ...keyCodes)
}
