package org.tb.gg.events.eventlog

import org.tb.gg.events.event.Event

interface EventLog {
    void addEvent(Event event)
    List<Event> getEvents()
    List<Event> getEventsByType(Class<? extends Event> eventType)
    void clear()
}
