package org.tb.gg.gameObject

class Keyboard extends BaseGameObject {
    Set<String> activeActions

    @Override
    void update(Long timestamp, Long delta) {
        super.update(timestamp, delta)

        activeActions = inputComponent.activeActions
    }
}
