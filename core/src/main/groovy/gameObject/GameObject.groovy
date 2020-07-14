package gameObject

import engine.helper.Updateable
import gameObject.components.RenderComponent
import groovy.transform.EqualsAndHashCode
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode
import renderer.renderObjects.Renderable

@EqualsAndHashCode(includes='id')
class GameObject implements Renderable, Updateable {
    Long id
    RenderComponent renderComponent

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
