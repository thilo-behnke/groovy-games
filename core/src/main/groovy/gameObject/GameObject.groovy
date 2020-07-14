package gameObject

import engine.helper.Updateable
import gameObject.components.RenderComponent
import global.geom.Vector
import groovy.transform.EqualsAndHashCode
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode
import renderer.renderObjects.Renderable

@EqualsAndHashCode(includes='id')
class GameObject implements Renderable, Updateable {
    Long id

    RenderComponent renderComponent

    Vector position

    void setRenderComponent(RenderComponent renderComponent) {
        renderComponent.setParent(this)
        this.renderComponent = renderComponent
    }

    @Override
    RenderNode render(RenderDestination renderDestination) {
        return renderComponent.render(renderDestination)
    }

    @Override
    void update(Long timestamp, Long delta) {

    }
}
