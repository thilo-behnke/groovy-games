package input.keyEvent


import io.reactivex.rxjava3.observers.TestObserver
import spock.lang.Specification
import spock.lang.Unroll

import javax.swing.*
import java.awt.*
import java.awt.event.KeyListener

@Unroll
class KeyEventJwtAdapterSpec extends Specification {

    KeyEventJwtAdapter keyEventJwtAdapter
    JFrame jFrameMock
    KeyListener keyListener

    def setup() {
        // TODO: How to override the addKeyListener method so that it can be used to be triggered from the test?
        jFrameMock = Mock(JFrame)
        jFrameMock.addKeyListener(_) >> {args ->
            keyListener = (KeyListener) args[0]
        }
        keyEventJwtAdapter = new KeyEventJwtAdapter(jFrameMock)

    }

    def 'should send out no key presses if not registered'() {
        when:
        TestObserver<Set<KeyEvent>> subscriber = keyEventJwtAdapter.pressedKeys().test()
        then:
        subscriber.assertValue(new HashSet<KeyEvent>())
    }

    def 'should register a keyListener on awt JFrame when registered'() {
        when:
        TestObserver<Set<KeyEvent>> subscriber = keyEventJwtAdapter.pressedKeys().test()
        keyEventJwtAdapter.register()
        then:
        subscriber.assertValue(new HashSet<KeyEvent>())
        1 * jFrameMock.addKeyListener(*_)
    }

    def 'should not emit a received key press it is not being listened to'() {
        given:
        def (keyCode, keyName) = [1, '_' as char]
        when:
        TestObserver<Set<KeyEvent>> subscriber = keyEventJwtAdapter.pressedKeys().test()
        keyEventJwtAdapter.register()
        sendKeyPressedEvent(keyCode, keyName)
        then:
        subscriber.assertValueCount(1)
        subscriber.assertValue((Set<KeyEvent>) [])
    }

    def 'should emit a received key press if it is being listened to'() {
        given:
        def (keyCode, keyName) = [1, '_' as char]
        when:
        TestObserver<Set<KeyEvent>> subscriber = keyEventJwtAdapter.pressedKeys().test()
        keyEventJwtAdapter.register()
        keyEventJwtAdapter.listenToKeys(keyCode)
        sendKeyPressedEvent(keyCode, keyName)
        then:
        subscriber.assertValueCount(2)
        subscriber.assertValues((Set<KeyEvent>) [], (Set<KeyEvent>) [new KeyEvent(keyCode: keyCode, keyName: keyName)])
    }

    def 'should emit a received key press if it is being listened to, but still ignore other key presses'() {
        given:
        def (keyCode, keyName) = [1, '_' as char]
        when:
        TestObserver<Set<KeyEvent>> subscriber = keyEventJwtAdapter.pressedKeys().test()
        keyEventJwtAdapter.register()
        keyEventJwtAdapter.listenToKeys(keyCode)
        sendKeyPressedEvent(2, '*' as char)
        sendKeyPressedEvent(keyCode, keyName)
        then:
        subscriber.assertValueCount(2)
        subscriber.assertValues((Set<KeyEvent>) [], (Set<KeyEvent>) [new KeyEvent(keyCode: keyCode, keyName: keyName)])
    }

    def 'should emit a received key press it is being listened to AFTER it was pressed'() {
        given:
        def (keyCode, keyName) = [1, '_' as char]
        when:
        TestObserver<Set<KeyEvent>> subscriber = keyEventJwtAdapter.pressedKeys().test()
        keyEventJwtAdapter.register()
        sendKeyPressedEvent(keyCode, keyName)
        keyEventJwtAdapter.listenToKeys(keyCode)
        then:
        subscriber.assertValueCount(2)
        subscriber.assertValues((Set<KeyEvent>) [], (Set<KeyEvent>) [new KeyEvent(keyCode: keyCode, keyName: keyName)])
    }

    def 'should remove pressed key once released'() {
        given:
        def (keyCode, keyName) = [1, '_' as char]
        when:
        TestObserver<Set<KeyEvent>> subscriber = keyEventJwtAdapter.pressedKeys().test()
        keyEventJwtAdapter.register()
        keyEventJwtAdapter.listenToKeys(keyCode)
        sendKeyPressedEvent(keyCode, keyName)
        sendKeyReleasedEvent(keyCode, keyName)
        then:
        subscriber.assertValueCount(3)
        subscriber.assertValues((Set<KeyEvent>) [], (Set<KeyEvent>) [new KeyEvent(keyCode: keyCode, keyName: keyName)], (Set<KeyEvent>) [])
    }

    def 'should remove pressed key once released and not update other key presses'() {
        given:
        def (keyCode, keyName) = [1, '_' as char]
        def (keyCode2, keyName2) = [2, '*' as char]
        when:
        TestObserver<Set<KeyEvent>> subscriber = keyEventJwtAdapter.pressedKeys().test()
        keyEventJwtAdapter.register()
        keyEventJwtAdapter.listenToKeys(keyCode, keyCode2)
        sendKeyPressedEvent(keyCode, keyName)
        sendKeyPressedEvent(keyCode2, keyName2)
        sendKeyReleasedEvent(keyCode, keyName)
        then:
        subscriber.assertValueCount(4)
        subscriber.assertValues((Set<KeyEvent>) [], (Set<KeyEvent>) [new KeyEvent(keyCode: keyCode, keyName: keyName)], (Set<KeyEvent>) [new KeyEvent(keyCode: keyCode, keyName: keyName), new KeyEvent(keyCode: keyCode2, keyName: keyName2)], (Set<KeyEvent>) [new KeyEvent(keyCode: keyCode2, keyName: keyName2)])
    }

    private sendKeyPressedEvent(int keyCode, char keyName) {
        keyListener.keyPressed(new java.awt.event.KeyEvent(new Component() {}, 0, 0L, 0, keyCode, keyName))
    }

    private sendKeyReleasedEvent(int keyCode, char keyName) {
        keyListener.keyReleased(new java.awt.event.KeyEvent(new Component() {}, 0, 0L, 0, keyCode, keyName))
    }
}
