package org.tb.gg.engine.framecache

import groovy.transform.EqualsAndHashCode
import org.tb.gg.gameObject.shape.Shape

@EqualsAndHashCode
class FrameState {
    Map<Long, Shape> gameObjectShapeCache

    Optional<Shape> getShape(Long gameObjectId) {
        Optional.ofNullable(gameObjectShapeCache.getOrDefault(gameObjectId, null))
    }
}
