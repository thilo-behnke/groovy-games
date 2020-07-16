package renderer.shape

import global.geom.Vector
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode

class Point implements Shape {
   Vector pos

   @Override
   RenderNode render(RenderDestination renderDestination) {
      return renderDestination.drawPoint(pos)
   }
}
