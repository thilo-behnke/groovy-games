package org.tb.gg.gameObject.components.body


import org.tb.gg.gameObject.shape.Shape

class ShapeBody extends Body {
    @Delegate
    Shape shape

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
