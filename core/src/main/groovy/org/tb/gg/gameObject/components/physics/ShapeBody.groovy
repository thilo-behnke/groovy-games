package org.tb.gg.gameObject.components.physics


import org.tb.gg.gameObject.shape.Shape

class ShapeBody extends Body<Shape> {
    private Shape shape

    Body(Shape shape) {
        this.shape = shape
    }

    @Override
    Shape getStructure() {
        return shape
    }

    @Override
    boolean collidesWith(Shape structure) {
        return shape.doesOverlapWith(structure)
    }

    @Override
    void update(Long timestamp, Long delta) {

    }
}
