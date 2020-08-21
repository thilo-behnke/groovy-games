package org.tb.gg.events

import org.tb.gg.events.event.AttackEvent
import org.tb.gg.events.event.AttackEventProperties
import org.tb.gg.events.event.PerishEvent
import org.tb.gg.events.event.PerishEventProperties
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import spock.lang.Specification

class DefaultEventManagerSpec extends Specification {
    DefaultEventManager defaultEventManager

    def setup() {
        defaultEventManager = new DefaultEventManager()
        defaultEventManager.init()
    }

    def cleanup() {
        defaultEventManager.destroy()
    }

    def 'events should be empty on initialized event manager'() {
        expect:
        defaultEventManager.getEvents().isEmpty()
    }

    def 'events by type should be empty on initialized event manager'() {
        expect:
        defaultEventManager.getEventsByType(AttackEvent).isEmpty()
    }

    def 'retrieving a sent event'() {
        when:
        defaultEventManager.sendEvents(attackEvent())
        then:
        defaultEventManager.getEvents().size() == 1
    }

    def 'send multiple events'() {
        when:
        defaultEventManager.sendEvents(attackEvent(), perishEvent())
        then:
        defaultEventManager.getEvents().size() == 2
    }

    def 'send multiple events, retrieve 1 by type'() {
        when:
        defaultEventManager.sendEvents(attackEvent(), perishEvent())
        then:
        defaultEventManager.getEventsByType(AttackEvent).size() == 1
    }

    def 'send multiple events, then clear'() {
        given:
        defaultEventManager.sendEvents(attackEvent(), perishEvent())
        when:
        defaultEventManager.clear()
        then:
        defaultEventManager.getEvents().isEmpty()
    }

    private static attackEvent() {
        def attacker = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody())
                .build()
        attacker.setId(1)
        def attacked = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody())
                .build()
        attacked.setId(2)

        new AttackEvent(new Date().getTime(), new AttackEventProperties(attacker: attacker, attacked: attacked))
    }

    private static perishEvent() {
        def attackEvent = attackEvent()
        new PerishEvent(new Date().getTime(), new PerishEventProperties(perished: attackEvent.props.attacked, reason: attackEvent))
    }
}
