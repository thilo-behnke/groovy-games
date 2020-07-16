package renderer.shape

import global.geom.Vector
import renderer.destination.RenderDestination
import renderer.options.RenderOptions
import renderer.renderObjects.RenderNode

class Point implements Shape {
   Vector pos

   @Override
   void render(RenderDestination renderDestination, RenderOptions options) {
      renderDestination.drawPoint(pos, options)
   }
}
