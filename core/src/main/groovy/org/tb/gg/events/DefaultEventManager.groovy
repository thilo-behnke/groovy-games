package org.tb.gg.events

import org.tb.gg.di.definition.Singleton
import org.tb.gg.events.event.Event
import org.tb.gg.events.eventlog.EventLog
import org.tb.gg.events.eventlog.EventLogList

class DefaultEventManager implements EventManager, Singleton {
    EventLog eventLog

    @Override
    void sendEvents(Event ...events) {
        events.each {
            eventLog.addEvent(it)
        }
    }

    @Override
    List<Event> getEvents() {
        return eventLog.getEvents()
    }

    @Override
    List<Event> getEventsByType(Class<? extends Event> eventType) {
        return eventLog.getEventsByType(eventType)
    }

    @Override
    void clear() {
        eventLog.clear()
    }

    @Override
    void init() {
        eventLog = new EventLogList()
    }

    @Override
    void destroy() {
        eventLog.clear()
    }
}
