package org.tb.gg.gameObject

class Keyboard extends GameObject {
    Set<String> activeActions

    @Override
    void update(Long timestamp, Long delta) {
        activeActions = inputComponent.activeActions
    }
}
