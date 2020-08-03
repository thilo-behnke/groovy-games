package org.tb.gg.gameObject.shape

import org.tb.gg.collision.Collidable
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
   Vector getCenter() {
      return pos
   }

   @Override
   Vector getClosestPointInDirectionFromCenter(Vector direction) {
       return pos
   }

   @Override
   boolean isPointWithin(Vector pos) {
      return this.pos == pos
   }

   @Override
   boolean doesOverlapWith(Collidable collidable) {
      return false
   }
}
