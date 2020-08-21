package org.tb.gg.events.event

abstract class Event<T> {
    Long timestamp
    T props

    Event(Long timestamp, T props) {
        this.timestamp = timestamp
        this.props = props
    }

    abstract String toEventString()
}
