package org.tb.gg.mocks

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.components.render.NoopRenderComponent
import org.tb.gg.gameObject.traits.InteractiveBody

class DummyGameObject extends BaseGameObject implements InteractiveBody {
    static create(ShapeBody body) {
        def dummy = new DummyGameObject()
        dummy.setBody(body)
        dummy.setRenderComponent(NoopRenderComponent.get())
        dummy.setInputComponent(NoopInputComponent.get())
        return dummy
    }
}
