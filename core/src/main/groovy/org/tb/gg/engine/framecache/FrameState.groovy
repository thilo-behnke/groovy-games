package org.tb.gg.engine.framecache

import groovy.transform.EqualsAndHashCode
import org.tb.gg.gameObject.shape.Shape

@EqualsAndHashCode
class FrameState {
    Map<Long, Shape> gameObjectShapeCache
}
