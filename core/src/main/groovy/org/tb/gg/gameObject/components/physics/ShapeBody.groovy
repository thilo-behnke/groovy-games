package org.tb.gg.gameObject.components.physics


import org.tb.gg.gameObject.shape.Shape

class ShapeBody extends Body<Shape> {
    private Shape shape

    ShapeBody (Shape shape) {
        this.shape = shape
    }

    Shape getStructure() {
        return shape
    }

    @Override
    void update(Long timestamp, Long delta) {

    }
}
