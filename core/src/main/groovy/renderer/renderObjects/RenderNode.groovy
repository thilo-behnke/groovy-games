package renderer.renderObjects

import renderer.options.RenderOptions

class RenderNode {
    // TODO: Should be optional, in this case the node has only structural purpose. Maybe add a string descriptor field?
    Optional<Renderable> renderObject
    Optional<RenderOptions> renderOptions
    List<RenderNode> childNodes = []
    Long order

    static RenderNode node(List<RenderNode> childNodes, Long order) {
        return node(childNodes, null, null, order)
    }

    static RenderNode node(List<RenderNode> childNodes, Renderable obj = null, RenderOptions options = null, Long order = -1L) {
        new RenderNode(renderObject: Optional.ofNullable(obj), renderOptions: Optional.ofNullable(options), childNodes: childNodes, order: order)
    }

    static RenderNode leaf(Renderable obj, RenderOptions options = RenderOptions.empty, Long order = -1L) {
        new RenderNode(renderObject: Optional.of(obj), renderOptions: Optional.of(options), childNodes: [], order: order)
    }

    static RenderNode leaf(Renderable obj, Long order) {
        new RenderNode(renderObject: Optional.of(obj), renderOptions: Optional.of(RenderOptions.empty), childNodes: [], order: order)
    }
}
