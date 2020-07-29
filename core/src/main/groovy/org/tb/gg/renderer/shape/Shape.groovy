package org.tb.gg.renderer.shape

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.Renderable

interface Shape extends Renderable {
    boolean isPointWithin(Vector pos);
//    boolean isShapeWithin(Shape shape);
//    boolean doesOverlapWith(Shape shape);
}