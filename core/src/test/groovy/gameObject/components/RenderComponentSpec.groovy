package gameObject.components

import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RenderComponentSpec extends Specification {
    class DummyRenderComponent extends RenderComponent {
        RenderNode node

        @Override
        RenderNode getRenderNode() {
            return node
        }
    }

    RenderDestination renderDestinationMock

    def setup() {
        renderDestinationMock = Mock(RenderDestination)
    }

    def 'should do nothing if the renderNode is empty and has no children'() {
        given:
        def renderComp = Spy(new DummyRenderComponent(node: RenderNode.node([])))
        when:
        renderComp.render(renderDestinationMock)
        then:
        1 * renderComp.performRender(renderComp.renderNode, renderDestinationMock)
    }
}
