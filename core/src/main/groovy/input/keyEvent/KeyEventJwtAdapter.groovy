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

    private final JFrame frame
    private final JwtKeyListener keyListener
    private final Set<Integer> keyCodesToListenTo = new HashSet<>()
    private Set<KeyEvent> keysPressed = new HashSet<>()
    private BehaviorSubject<Set<KeyEvent>> source

    KeyEventJwtAdapter(JFrame frame) {
        this.frame = frame
        this.keyListener = new JwtKeyListener(this)
        this.source = BehaviorSubject.createDefault(new HashSet<>())
    }

    @Override
    void register() {
        this.frame.addKeyListener(keyListener)
    }

    @Override
    void unregister() {
        keysPressed.clear()
        this.source.onNext(keysPressed)
        this.frame.removeKeyListener(keyListener)
    }

    void alertKeyPressed(java.awt.event.KeyEvent event) {
        keysPressed = keysPressed + new KeyEvent(keyCode: event.keyCode, keyName: event.keyChar)
        this.source.onNext(keysPressed)
    }

    void alertKeyReleased(java.awt.event.KeyEvent event) {
        keysPressed = keysPressed - new KeyEvent(keyCode: event.keyCode, keyName: event.keyChar)
        this.source.onNext(keysPressed)
    }

    @Override
    Observable<Set<KeyEvent>> pressedKeys() {
        // Ignore key presses to which should not be listened.
        return source
                .<Set<KeyEvent>> map((Set<KeyEvent> events) ->
                        events.findAll {
                            event -> keyCodesToListenTo.find { it == event.keyCode }
                        }
                )
                .distinctUntilChanged()
    }

    @Override
    void listenToKeys(Integer ...keyCodes) {
        this.keyCodesToListenTo.addAll(keyCodes)
        this.source.onNext(keysPressed)
    }

    @Override
    void stopListeningToKeys(Integer ...keyCodes) {
        this.keyCodesToListenTo.removeAll(keyCodes)
        this.source.onNext(keysPressed)
    }
}