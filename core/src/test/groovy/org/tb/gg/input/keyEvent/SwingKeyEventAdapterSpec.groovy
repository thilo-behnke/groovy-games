package org.tb.gg.input.keyEvent

import org.tb.gg.di.ServiceProvider
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.EnvironmentSettings
import org.tb.gg.input.Key
import org.tb.gg.input.awt.SwingKeyEventAdapter
import io.reactivex.rxjava3.observers.TestObserver
import spock.lang.Specification
import spock.lang.Unroll

import javax.swing.*
import java.awt.*
import java.awt.event.KeyListener

@Unroll
class SwingKeyEventAdapterSpec extends Specification {

    SwingKeyEventAdapter keyEventJwtAdapter
    JFrame jFrameMock
    KeyListener keyListener
    TestObserver<Set<Key>> keyPressObserver

    def setup() {

        jFrameMock = Mock(JFrame)
        jFrameMock.addKeyListener(_) >> {args ->
            keyListener = (KeyListener) args[0]
        }

        // TODO: Hotfix, This should also be handled by DI.
        def environmentService = Mock(EnvironmentService)
        environmentService.getEnvironment() >> {args -> new EnvironmentSettings(graphicsAPI: org.tb.gg.env.Graphics.SWING, environmentFrame: jFrameMock)}
        ServiceProvider.registerSingletonService(environmentService, EnvironmentService.getSimpleName())

        keyEventJwtAdapter = new SwingKeyEventAdapter()
    }

    def cleanup() {
        keyPressObserver.dispose()
        ServiceProvider.reset()
    }

    def 'should not register a keyListener on awt JFrame if not registered'() {
        when:
        subscribeKeyPresses()
        then:
        assertKeyPressesReceived(new HashSet<Key>())
    }

    def 'should register a keyListener on awt JFrame when registered'() {
        when:
        subscribeKeyPresses()
        keyEventJwtAdapter.init()
        then:
        assertKeyPressesReceived(new HashSet<Key>())
        1 * jFrameMock.addKeyListener(*_)
    }

    def 'should not emit a received key press it is not being listened to'() {
        when:
        subscribeKeyPresses()
        keyEventJwtAdapter.init()
        sendUpKeyPressed()
        then:
        assertKeyPressesReceived(new HashSet<Key>())
    }

    def 'should emit a received key press if it is being listened to'() {
        when:
        subscribeKeyPresses()
        keyEventJwtAdapter.init()
        listenToUp()
        sendUpKeyPressed()
        then:
        assertKeyPressesReceived((Set<Key>) [], (Set<Key>) [Key.UP])
    }

    def 'should emit a received key press if it is being listened to, but still ignore other key presses'() {
        when:
        subscribeKeyPresses()
        keyEventJwtAdapter.init()
        listenToUp()
        sendUpKeyPressed()
        sendDownKeyPressed()
        then:
        assertKeyPressesReceived((Set<Key>) [], (Set<Key>) [Key.UP])
    }

    def 'should emit a received key press it is being listened to AFTER it was pressed'() {
        when:
        subscribeKeyPresses()
        keyEventJwtAdapter.init()
        sendUpKeyPressed()
        listenToUp()
        then:
        assertKeyPressesReceived((Set<Key>) [], (Set<Key>) [Key.UP])
    }

    def 'should be able to listen to multiple keys'() {
        when:
        subscribeKeyPresses()
        keyEventJwtAdapter.init()
        listenToUp()
        listenToDown()
        sendUpKeyPressed()
        sendDownKeyPressed()
        then:
        assertKeyPressesReceived((Set<Key>) [], (Set<Key>) [Key.UP], (Set<Key>) [Key.UP, Key.DOWN])
    }

    def 'should remove pressed key once released'() {
        when:
        subscribeKeyPresses()
        keyEventJwtAdapter.init()
        listenToUp()
        sendUpKeyPressed()
        sendUpKeyReleased()
        then:
        assertKeyPressesReceived((Set<Key>) [], (Set<Key>) [Key.UP], (Set<Key>) [])
    }

    def 'should remove pressed key once released and not update other key presses'() {
        when:
        subscribeKeyPresses()
        keyEventJwtAdapter.init()
        listenToUpAndDown()
        sendUpKeyPressed()
        sendDownKeyPressed()
        sendUpKeyReleased()
        then:
        assertKeyPressesReceived((Set<Key>) [], (Set<Key>) [Key.UP], (Set<Key>) [Key.UP, Key.DOWN], (Set<Key>) [Key.DOWN])
    }

    private subscribeKeyPresses() {
        keyPressObserver = keyEventJwtAdapter.pressedKeys().test()
    }

    private assertKeyPressesReceived(Set<Key> ...keySet) {
        keyPressObserver.assertValues(keySet)
    }

    private listenToUp() {
        keyEventJwtAdapter.listenToKeys(Key.UP)
    }

    private listenToDown() {
        keyEventJwtAdapter.listenToKeys(Key.DOWN)
    }

    private listenToUpAndDown() {
        keyEventJwtAdapter.listenToKeys(Key.UP, Key.DOWN)
    }

    private sendUpKeyPressed() {
       sendKeyPressedEvent(java.awt.event.KeyEvent.VK_UP, '_' as char)
    }

    private sendDownKeyPressed() {
        sendKeyPressedEvent(java.awt.event.KeyEvent.VK_DOWN, '_' as char)
    }

    private sendUpKeyReleased() {
        sendKeyReleasedEvent(java.awt.event.KeyEvent.VK_UP, '_' as char)
    }

    private sendDownKeyReleased() {
        sendKeyReleasedEvent(java.awt.event.KeyEvent.VK_DOWN, '_' as char)
    }

    private sendKeyPressedEvent(int keyCode, char keyName) {
        keyListener.keyPressed(new java.awt.event.KeyEvent(new Component() {}, 0, 0L, 0, keyCode, keyName))
    }

    private sendKeyReleasedEvent(int keyCode, char keyName) {
        keyListener.keyReleased(new java.awt.event.KeyEvent(new Component() {}, 0, 0L, 0, keyCode, keyName))
    }
}
