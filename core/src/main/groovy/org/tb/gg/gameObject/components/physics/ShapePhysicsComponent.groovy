package org.tb.gg.gameObject.components.physics


import org.tb.gg.gameObject.shape.Shape

// TODO: Remove, doesn't make sense anymore as the game object has the body.
class ShapePhysicsComponent extends PhysicsComponent<Shape> {
    private ShapeBody body

    ShapePhysicsComponent() {
    }
}
