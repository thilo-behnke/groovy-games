package org.tb.gg.gameObject.shape

import groovy.transform.EqualsAndHashCode
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

@EqualsAndHashCode
class Point extends Shape {
   Vector pos

   @Override
   void render(RenderDestination renderDestination, RenderOptions options) {
      renderDestination.drawPoint(pos, options)
   }

   @Override
   Vector getCenter() {
      return pos
   }

   @Override
   void setCenter(Vector pos) {
      this.pos = pos
   }

   @Override
   boolean isPointWithin(Vector pos) {
      return this.pos == pos
   }

   @Override
   Rect getBoundingRect() {
      return new Rect(pos, Vector.zeroVector())
   }

   @Override
   Shape copy() {
      return new Point(pos: pos.copy())
   }

   @Override
   void rotate(BigDecimal radians) {
      // Left intentionally empty, there is no point in rotating a point.
   }

   @Override
   void setRotation(BigDecimal rotation) {
      // Left intentionally empty, there is no point in rotating a point.
   }
}
