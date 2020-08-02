package org.tb.gg.renderer.shape

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.Renderable

// TODO: It would be great to model mouse interaction with a trait, but traits don't allow ast transformations.
interface Shape extends Renderable {

    abstract boolean isPointWithin(Vector pos);
//    boolean isShapeWithin(Shape shape);
//    boolean doesOverlapWith(Shape shape);
}