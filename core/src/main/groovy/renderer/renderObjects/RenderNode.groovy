package renderer.renderObjects

import renderer.options.RenderOptions

class RenderNode {
    // TODO: Should be optional, in this case the node has only structural purpose. Maybe add a string descriptor field?
    Renderable renderObject
    RenderOptions renderOptions
    List<RenderNode> childNodes = []

    static RenderNode node(Renderable obj, RenderOptions options, List<RenderNode> childNodes) {
        new RenderNode(renderObject: obj, renderOptions: options, childNodes: childNodes)
    }

    static RenderNode leaf(Renderable obj, RenderOptions options = RenderOptions.empty) {
        new RenderNode(renderObject: obj, renderOptions: options, childNodes: [])
    }
}
