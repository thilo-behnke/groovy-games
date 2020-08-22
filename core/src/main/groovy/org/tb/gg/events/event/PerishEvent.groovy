package org.tb.gg.events.event

import org.tb.gg.gameObject.GameObject

class PerishEvent extends Event<PerishEventProperties> {
    PerishEvent(Long timestamp, PerishEventProperties props) {
        super(timestamp, props)
    }

    @Override
    String toEventString() {
        return "${props.perished} perished. Reason: ${props.reason.toEventString()}"
    }
}

class PerishEventProperties {
    GameObject perished
    Event reason
}
