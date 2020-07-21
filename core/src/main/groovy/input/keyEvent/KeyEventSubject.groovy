package input.keyEvent

import input.keyEvent.KeyEvent
import io.reactivex.rxjava3.core.Observable

interface KeyEventSubject {
    Observable<Set<KeyEvent>> pressedKeys()

    void register()
    void unregister()

    void listenToKey(Integer keyCode)
    void listenToKeys(List<Integer> keyCode)
    void stopListeningToKey(Integer keyCode)
    void stopListeningToKeys(List<Integer> keyCodes)
}
