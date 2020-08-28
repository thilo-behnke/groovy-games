package org.tb.gg.renderer.destination

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.RenderOptions

import java.awt.image.BufferedImage

interface RenderDestination<I> {
    void setDimensions(int width, int height)
    // TODO: Command pattern for render options?
    void drawImage(I image, Vector pos, RenderOptions options)
    void drawLine(Vector start, Vector end, RenderOptions options)
    void drawPoint(Vector pos, RenderOptions options)
    void drawCircle(Vector center, BigDecimal radius, RenderOptions options)
    void drawRect(Vector topLeft, Vector dim, RenderOptions options)
    void drawText(Vector pos, String text, RenderOptions options)

    void refresh()
}

