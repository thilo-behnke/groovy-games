package org.tb.gg.renderer.shape

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.Renderable

interface Shape extends Renderable {

    abstract boolean isPointWithin(Vector pos);
    // TODO: Implement for collision detection between shapes.
//    boolean isShapeWithin(Shape shape);
//    boolean doesOverlapWith(Shape shape);
}