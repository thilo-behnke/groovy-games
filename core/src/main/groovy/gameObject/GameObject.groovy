package gameObject

import engine.helper.Updateable
import gameObject.components.InputComponent
import gameObject.components.RenderComponent
import global.geom.Vector
import groovy.transform.EqualsAndHashCode
import renderer.destination.RenderDestination

@EqualsAndHashCode(includes='id')
class GameObject implements Updateable {
    Long id

    RenderComponent renderComponent
    InputComponent inputComponent

    void setInputComponent(InputComponent inputComponent) {
        this.inputComponent = inputComponent
    }

    void setRenderComponent(RenderComponent renderComponent) {
        renderComponent.setParent(this)
        this.renderComponent = renderComponent
    }

    void render(RenderDestination renderDestination) {
        renderComponent.render(renderDestination)
    }

    @Override
    void update(Long timestamp, Long delta) {
        def activeActions = inputComponent.activeActions()
        // TODO: Update based on active actions.
        // TODO: Integrate state machine.
    }
}
