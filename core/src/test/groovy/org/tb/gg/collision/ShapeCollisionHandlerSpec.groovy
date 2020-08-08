package org.tb.gg.collision

import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.global.geom.Vector
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ShapeCollisionHandlerSpec extends Specification {

    ShapeCollisionDetectorSpec shapeCollisionDetector

    void setup() {
       shapeCollisionDetector = new ShapeCollisionDetectorSpec()
    }
}
