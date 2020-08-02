package org.tb.gg.gameObject

import org.tb.gg.gameObject.GameObject
import org.tb.gg.global.geom.Vector

class CircleGameObject extends GameObject {
    Vector center
    BigDecimal radius

    @Override
    void update(Long timestamp, Long delta) {
        def activeActions = inputComponent.getActiveActions()
        System.println(activeActions)
    }
}
