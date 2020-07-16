package renderer.renderObjects

import renderer.options.RenderOptions

class RenderNode {
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
