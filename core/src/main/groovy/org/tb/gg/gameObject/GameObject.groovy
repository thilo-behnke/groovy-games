package org.tb.gg.gameObject

import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.gameObject.lifecycle.Lifecycle

interface GameObject extends Updateable, Lifecycle, Perishable  {
    void setId(long id)
    ShapeBody getBody()
    RenderComponent getRenderComponent()
    PhysicsComponent getPhysicsComponent()
    InputComponent getInputComponent()
}