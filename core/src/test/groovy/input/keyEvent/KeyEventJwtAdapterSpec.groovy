package input.keyEvent

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subscribers.TestSubscriber
import spock.lang.Specification
import spock.lang.Unroll

import javax.swing.JFrame
import java.awt.event.KeyListener

@Unroll
class KeyEventJwtAdapterSpec extends Specification {

    KeyEventJwtAdapter keyEventJwtAdapter
    JFrame jFrameMock

    def setup() {
        // TODO: How to override the addKeyListener method so that it can be used to be triggered from the test?
        jFrameMock = Mock(JFrame)
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

    def 'should emit a single key when a single key press was notified by awt JFrame'() {
        when:
        TestObserver<Set<KeyEvent>> subscriber = keyEventJwtAdapter.pressedKeys().test()
        keyEventJwtAdapter.register()
        keyEventJwtAdapter.listenToKey(1)
        then:
        subscriber.assertValueCount(2)
        subscriber.assertValue((Set<KeyEvent>) [new KeyEvent(keyCode: 1, keyName: 'str')])
    }
}
