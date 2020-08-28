package org.tb.gg.gameObject.components.body

import org.tb.gg.gameObject.shape.Shape

class SpriteBody extends Body {

    private Shape shape

    protected SpriteBody(Shape shape) {
        this.shape = shape
    }

    @Override
    void update(Long timestamp, Long delta) {

    }
}
