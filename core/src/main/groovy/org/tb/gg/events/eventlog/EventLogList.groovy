package org.tb.gg.events.eventlog

import org.tb.gg.events.event.Event

class EventLogList implements EventLog {
    private List<Event> eventLog = new ArrayList<Event>()

    @Override
    void addEvent(Event event) {
        eventLog.add(event)
    }

    @Override
    List<Event> getEvents() {
        return eventLog
    }

    @Override
    List<Event> getEventsByType(Class<? extends Event> eventType) {
        eventLog.findAll { (it.class == eventType) }
    }

    @Override
    void clear() {
        eventLog.clear()
    }
}
