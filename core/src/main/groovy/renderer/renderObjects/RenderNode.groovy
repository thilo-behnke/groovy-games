package renderer.renderObjects

import renderer.options.RenderOptions

class RenderNode {
    // TODO: Should be optional, in this case the node has only structural purpose. Maybe add a string descriptor field?
    Optional<Renderable> renderObject
    Optional<RenderOptions> renderOptions
    List<RenderNode> childNodes = []

    static RenderNode node(List<RenderNode> childNodes, Renderable obj = null, RenderOptions options = null) {
        new RenderNode(renderObject: Optional.ofNullable(obj), renderOptions: Optional.ofNullable(options), childNodes: childNodes)
    }

    static RenderNode leaf(Renderable obj, RenderOptions options = RenderOptions.empty) {
        new RenderNode(renderObject: Optional.of(obj), renderOptions: Optional.of(options), childNodes: [])
    }
}
