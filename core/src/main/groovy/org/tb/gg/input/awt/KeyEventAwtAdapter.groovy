package org.tb.gg.input.awt

import org.tb.gg.input.Key
import org.tb.gg.input.keyEvent.KeyEventSubject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

import javax.swing.JFrame
import java.awt.event.KeyListener

class KeyEventAwtAdapter implements KeyEventSubject {
    class JwtKeyListener implements KeyListener {

        private KeyEventAwtAdapter parent

        JwtKeyListener(KeyEventAwtAdapter parent) {
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
    private Set<Key> keysPressed = new HashSet<>()
    private BehaviorSubject<Set<Key>> source

    KeyEventAwtAdapter(JFrame frame) {
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
        def keyForCode = AwtKeyCodeConverter.convertAwtKeyCodesToKeys(event.keyCode)
        keysPressed = keysPressed + keyForCode
        this.source.onNext(keysPressed)
    }

    void alertKeyReleased(java.awt.event.KeyEvent event) {
        def keyForCode = AwtKeyCodeConverter.convertAwtKeyCodesToKeys(event.keyCode)
        keysPressed = keysPressed - keyForCode
        this.source.onNext(keysPressed)
    }

    @Override
    Observable<Set<Key>> pressedKeys() {
        // Ignore key presses to which should not be listened.
        return source
                .<Set<Key>> map((Set<Key> keys) ->
                        keys.findAll { key ->
                            def keyCodeForKey = AwtKeyCodeConverter.convertKeysToAwtKeyCodes(key).first()
                            keyCodesToListenTo.find { it == keyCodeForKey }
                        }
                )
                .distinctUntilChanged()
    }

    @Override
    void listenToKeys(Key ...keys) {
        def awtKeyCodes = AwtKeyCodeConverter.convertKeysToAwtKeyCodes(keys)
        this.keyCodesToListenTo.addAll(awtKeyCodes)
        this.source.onNext(keysPressed)
    }

    @Override
    void stopListeningToKeys(Key ...keys) {
        def awtKeyCodes = AwtKeyCodeConverter.convertKeysToAwtKeyCodes(keys)
        this.keyCodesToListenTo.removeAll(keys)
        this.source.onNext(keysPressed)
    }
}