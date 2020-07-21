package input.keyEvent


import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

import javax.swing.JFrame
import java.awt.event.KeyListener

class KeyEventJwtAdapter implements KeyEventSubject {
    class JwtKeyListener implements KeyListener {

        private KeyEventJwtAdapter parent

        JwtKeyListener(KeyEventJwtAdapter parent) {
            this.parent = parent
        }

        @Override
        void keyTyped(java.awt.event.KeyEvent e) {
            // Ignore for now.
        }

        @Override
        void keyPressed(java.awt.event.KeyEvent e) {
            parent.alertKeyPressed(e)

        }

        @Override
        void keyReleased(java.awt.event.KeyEvent e) {
            parent.alertKeyReleased(e)
        }
    }
    // TODO: What does this error mean?
    private final JFrame frame
    private final JwtKeyListener keyListener
    private final Set<Integer> keyCodesToListenTo = new HashSet<>()
    private BehaviorSubject<Set<KeyEvent>> source

    KeyEventJwtAdapter(JFrame frame) {
        this.frame = frame
        this.keyListener = new JwtKeyListener(this)
    }

    @Override
    void register() {
        this.source = BehaviorSubject.create()
        this.frame.addKeyListener(keyListener)
    }

    @Override
    void unregister() {
        this.source.onComplete()
        this.frame.removeKeyListener(keyListener)
    }

    void alertKeyPressed(java.awt.event.KeyEvent event) {
        // Ignore key presses to which should not be listened.
        if (!keyCodesToListenTo.find { it == event.keyCode }) {
            return
        }
        def currentKeysPressed = this.source.getValue()
        def updatedKeysPressed = currentKeysPressed + new KeyEvent(keyCode: event.keyCode, keyName: event.keyChar)
        this.source.onNext(updatedKeysPressed)
    }

    void alertKeyReleased(java.awt.event.KeyEvent event) {
        // Ignore key releases to which should not be listened.
        if (!keyCodesToListenTo.find { it == event.keyCode }) {
            return
        }
        def currentKeysPressed = this.source.getValue()
        def updatedKeysPressed = currentKeysPressed - new KeyEvent(keyCode: event.keyCode, keyName: event.keyChar)
        this.source.onNext(updatedKeysPressed)
    }

    @Override
    Observable<Set<KeyEvent>> pressedKeys() {
        return source
    }

    @Override
    void listenToKey(Integer keyCode) {
        frame.addKeyListener()
    }

    @Override
    void listenToKeys(List<Integer> keyCode) {
        this.keyCodesToListenTo + keyCode
    }

    @Override
    void stopListeningToKey(Integer keyCode) {
        this.keyCodesToListenTo - keyCode
    }

    @Override
    void stopListeningToKeys(List<Integer> keyCodes) {
        this.keyCodesToListenTo - keyCodes
    }
}