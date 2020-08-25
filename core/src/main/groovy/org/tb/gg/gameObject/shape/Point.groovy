package org.tb.gg.gameObject.shape


import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

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
      return new Point(pos: new Vector(x: pos.x, y: pos.y))
   }
}
