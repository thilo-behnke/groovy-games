package gameObject.components

import renderer.destination.RenderDestination
import renderer.options.DrawColor
import renderer.options.RenderOptions
import renderer.renderObjects.RenderNode
import renderer.renderObjects.Renderable
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
    Renderable dummyRenderObj1
    Renderable dummyRenderObj2
    Renderable dummyRenderObj3
    Renderable dummyRenderObj4

    def setup() {
        renderDestinationMock = Mock(RenderDestination)
        dummyRenderObj1 = Mock(Renderable)
        dummyRenderObj2 = Mock(Renderable)
        dummyRenderObj3 = Mock(Renderable)
        dummyRenderObj4 = Mock(Renderable)
    }

    def 'should do nothing if the renderNode is empty and has no children'() {
        given:
        def renderComp = getEmptyRenderComp()
        when:
        renderComp.render(renderDestinationMock)
        then:
        1 * renderComp.performRender(renderComp.renderNode, renderDestinationMock)
    }

    def 'should only render the given node if it is not empty but has no children (leaf)'() {
        given:
        def renderComp = getLeafRenderComp()
        when:
        renderComp.render(renderDestinationMock)
        then:
        1 * renderComp.performRender(renderComp.renderNode, renderDestinationMock)
        1 * dummyRenderObj1.render(renderDestinationMock, RenderOptions.empty)
    }

    def 'should render the given node if it is not empty including its children (node)'() {
        given:
        def renderComp = getNodeRenderComp()
        when:
        renderComp.render(renderDestinationMock)
        then:
        1 * dummyRenderObj1.render(renderDestinationMock, RenderOptions.empty)
        then:
        1 * dummyRenderObj2.render(renderDestinationMock, RenderOptions.empty)
        then:
        1 * dummyRenderObj3.render(renderDestinationMock, RenderOptions.empty)
    }

    def 'should render the given node children, respecting their order'() {
        given:
        def renderComp = getNodeRenderCompWithRenderOptions()
        when:
        renderComp.render(renderDestinationMock)
        then:
        1 * dummyRenderObj1.render(renderDestinationMock, RenderOptions.empty)
        1 * dummyRenderObj2.render(renderDestinationMock, new RenderOptions(drawColor: DrawColor.YELLOW))
        1 * dummyRenderObj3.render(renderDestinationMock, new RenderOptions(drawColor: DrawColor.RED))
        1 * dummyRenderObj4.render(renderDestinationMock, new RenderOptions(drawColor: DrawColor.BLACK))
    }

    def 'should pass the correct renderOptions for each node'() {

    }

    private getEmptyRenderComp() {
        Spy(new DummyRenderComponent(node: RenderNode.node([])))
    }

    private getLeafRenderComp() {
        Spy(new DummyRenderComponent(node: RenderNode.leaf(dummyRenderObj1)))
    }

    private getNodeRenderComp() {
        Spy(new DummyRenderComponent(node: RenderNode.node(
                [RenderNode.leaf(dummyRenderObj2), RenderNode.node([RenderNode.leaf(dummyRenderObj3)])],
                dummyRenderObj1
        )))
    }

    private getNodeRenderCompWithOrderedChildren() {
        Spy(new DummyRenderComponent(node: RenderNode.node(
                [RenderNode.leaf(dummyRenderObj2, 3), RenderNode.node([RenderNode.leaf(dummyRenderObj3)], 1), RenderNode.leaf(dummyRenderObj4, 2)],
                dummyRenderObj1
        )))
    }

    private getNodeRenderCompWithRenderOptions() {
        Spy(new DummyRenderComponent(node: RenderNode.node(
                [
                        RenderNode.leaf(dummyRenderObj2, new RenderOptions(drawColor: DrawColor.YELLOW)),
                        RenderNode.node([RenderNode.leaf(dummyRenderObj3, new RenderOptions(drawColor: DrawColor.RED))]),
                        RenderNode.leaf(dummyRenderObj4, new RenderOptions(drawColor: DrawColor.BLACK))
                ],
                dummyRenderObj1
        )))
    }


}
