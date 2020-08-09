package org.tb.gg.mocks

import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.render.NoopRenderComponent
import org.tb.gg.gameObject.traits.InteractiveBody

class DummyGameObject extends GameObject implements InteractiveBody {
    static create(ShapeBody body) {
        def dummy = new DummyGameObject()
        dummy.setBody(body)
        dummy.setRenderComponent(NoopRenderComponent.get())
        dummy.setInputComponent(NoopInputComponent.get())
        return dummy
    }
}
