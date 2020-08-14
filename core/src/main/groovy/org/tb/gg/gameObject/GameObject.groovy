package org.tb.gg.gameObject

import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.gameObject.traits.Perishable

interface GameObject extends Updateable, Lifecycle, Perishable  {
    ShapeBody getBody()
    RenderComponent getRenderComponent()
    PhysicsComponent getPhysicsComponent()
    InputComponent getInputComponent()
}