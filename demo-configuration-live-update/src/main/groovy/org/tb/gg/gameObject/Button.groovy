package org.tb.gg.gameObject

import org.tb.gg.gameObject.components.ButtonRenderComponent
import org.tb.gg.gameObject.components.InputComponent
import org.tb.gg.gameObject.components.NoopInputComponent
import org.tb.gg.global.geom.Vector
import org.tb.gg.input.actions.NoopInputActionProvider

class Button extends GameObject {
    Vector pos
    Vector dim

    static Button create(Vector pos, Vector dim) {
       def button = new Button(pos: pos, dim: dim)
        button.setRenderComponent(new ButtonRenderComponent(pos, dim))
        button.setInputComponent(new NoopInputComponent())
        return button
    }
}
