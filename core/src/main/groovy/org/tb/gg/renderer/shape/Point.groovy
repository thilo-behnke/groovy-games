package org.tb.gg.renderer.shape

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Point implements Shape {
   Vector pos

   @Override
   void render(RenderDestination renderDestination, RenderOptions options) {
      renderDestination.drawPoint(pos, options)
   }

   @Override
   boolean isPointWithin(Vector pos) {
      return this.pos == pos
   }
}
