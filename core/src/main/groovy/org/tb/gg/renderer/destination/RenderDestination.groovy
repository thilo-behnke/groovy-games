package org.tb.gg.renderer.destination


import org.tb.gg.renderer.options.RenderOptions

interface RenderDestination<Vector> {
    void setDimensions(int width, int height)
    // TODO: Command pattern for render options?
    void drawLine(Vector start, Vector end, RenderOptions options)
    void drawPoint(Vector pos, RenderOptions options)
    void drawCircle(Vector center, BigDecimal radius, RenderOptions options)

    void refresh()
}

