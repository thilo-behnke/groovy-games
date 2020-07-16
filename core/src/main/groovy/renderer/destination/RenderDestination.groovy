package renderer.destination


import renderer.options.RenderOptions

interface RenderDestination<Vector> {
    // TODO: Command pattern for render options?
    void drawLine(Vector start, Vector end, RenderOptions options)
    void drawPoint(Vector pos, RenderOptions options)
    void drawCircle(Vector center, Float radius, RenderOptions options)

    void refresh()
}

