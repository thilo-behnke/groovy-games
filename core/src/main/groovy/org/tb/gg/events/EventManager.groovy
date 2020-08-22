package org.tb.gg.events

import org.tb.gg.events.event.Event

interface EventManager {
    void sendEvents(Event ...events)
    List<Event> getEvents()
    List<Event> getEventsByType(Class<? extends Event> eventType)
    void clear()
}