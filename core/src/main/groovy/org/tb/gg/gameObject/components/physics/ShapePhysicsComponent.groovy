package org.tb.gg.gameObject.components.physics


import org.tb.gg.gameObject.shape.Shape

class ShapePhysicsComponent extends PhysicsComponent<Shape> {
    private ShapeBody body

    ShapePhysicsComponent(ShapeBody body) {
        this.body = body
    }

    @Override
    Body getBody() {
        return body
    }

    @Override
    Shape getStructure() {
        return body.getStructure()
    }

    @Override
    boolean collidesWith(Shape structure) {
        return body.collidesWith(structure)
    }
}
