package renderer.renderObjects

class RenderNode {
    Renderable renderObject
    List<RenderNode> childNodes

    static RenderNode leaf(Renderable obj) {
        new RenderNode(renderObject: obj, childNodes: [])
    }

    boolean hasChildNodes () {
        return childNodes.size()
    }
}
