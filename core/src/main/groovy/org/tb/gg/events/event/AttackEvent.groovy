package org.tb.gg.events.event


import org.tb.gg.gameObject.GameObject

class AttackEvent extends Event<AttackEventProperties> {
    AttackEvent(Long timestamp, AttackEventProperties eventProperties) {
        super(timestamp, eventProperties)
    }

    @Override
    String toEventString() {
        "${props.attacker.getId()} attacked ${props.attacked.getId()} and dealt ${props.damageTaken} damage."
    }
}

class AttackEventProperties {
    GameObject attacker
    GameObject attacked
    int damageTaken
}