package org.tb.gg.gameObject

import org.tb.gg.gameObject.components.RectButtonRenderComponent
import org.tb.gg.gameObject.components.NoopInputComponent
import org.tb.gg.global.geom.Vector

class RectButton extends GameObject {
    Vector pos
    Vector dim

    static RectButton create(Vector pos, Vector dim) {
       def button = new RectButton(pos: pos, dim: dim)
        button.setRenderComponent(new RectButtonRenderComponent(pos, dim))
        button.setInputComponent(new NoopInputComponent())
        return button
    }
}
