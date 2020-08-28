package org.tb.gg.gameObject

import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.components.body.Body
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector
import org.tb.gg.utils.Stack

interface GameObject extends Updateable, Lifecycle, Perishable {
    Long getId()
    void setId(long id)
    void setOrientation(Vector orientation)
    Stack<Shape> getPreviousShapeStates()
    Vector getOrientation()

    Body getBody()
    RenderComponent getRenderComponent()
    PhysicsComponent getPhysicsComponent()
    InputComponent getInputComponent()
}