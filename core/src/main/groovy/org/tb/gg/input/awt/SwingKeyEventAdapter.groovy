package org.tb.gg.input.awt

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.input.Key
import org.tb.gg.input.keyEvent.KeyEventSubject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

import javax.swing.JFrame
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class SwingKeyEventAdapter implements KeyEventSubject {
    class SwingKeyListener implements KeyListener {

        private SwingKeyEventAdapter parent

        SwingKeyListener(SwingKeyEventAdapter parent) {
            this.parent = parent
        }

        @Override
        void keyTyped(KeyEvent e) {
            // Ignore for now.
        }

        @Override
        void keyPressed(KeyEvent e) {
            parent.alertKeyPressed(e)
        }

        @Override
        void keyReleased(KeyEvent e) {
            parent.alertKeyReleased(e)
        }
    }

    @Inject
    private EnvironmentService environmentService

    private final JFrame frame
    private final SwingKeyListener keyListener
    private final Set<Integer> keyCodesToListenTo = new HashSet<>()
    private Set<Key> keysPressed = new HashSet<>()
    private BehaviorSubject<Set<Key>> source

    SwingKeyEventAdapter() {
        this.frame = (JFrame) environmentService.environment.environmentFrame
        this.keyListener = new SwingKeyListener(this)
        this.source = BehaviorSubject.createDefault(new HashSet<>())
    }

    @Override
    void onInit() {
        this.frame.addKeyListener(keyListener)
    }

    @Override
    void onDestroy() {
        keysPressed.clear()
        this.source.onNext(keysPressed)
        this.frame.removeKeyListener(keyListener)
    }

    void alertKeyPressed(KeyEvent event) {
        def keyForCode = AwtKeyCodeConverter.convertAwtKeyCodesToKeys(event.keyCode)
        keysPressed = keysPressed + keyForCode
        this.source.onNext(keysPressed)
    }

    void alertKeyReleased(KeyEvent event) {
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
                            def matchingKeyCodesForKey = AwtKeyCodeConverter.convertKeysToAwtKeyCodes(key)
                            if(matchingKeyCodesForKey.size() == 0) {
                                return false
                            }
                            def keyCodeForKey = matchingKeyCodesForKey.first()
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
        this.keyCodesToListenTo.removeAll(awtKeyCodes)
        this.source.onNext(keysPressed)
    }
}