package org.tb.gg.renderer.destination

import org.tb.gg.di.definition.Singleton
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.BaseImage
import org.tb.gg.renderer.options.RenderOptions

import java.awt.image.BufferedImage

interface RenderDestination<I extends BaseImage> extends Singleton {
    void setDimensions(int width, int height)
    Vector getDimensions()
    Vector getMousePosition()

    // TODO: Command pattern for render options?
    void drawImage(I image, Vector topLeft, BigDecimal rotation, RenderOptions options)
    void drawLine(Vector start, Vector end, RenderOptions options)
    void drawPoint(Vector pos, RenderOptions options)
    void drawCircle(Vector center, BigDecimal radius, RenderOptions options)
    void drawRect(Vector topLeft, Vector dim, Float rotation, RenderOptions options)
    void drawText(Vector pos, String text, RenderOptions options)

    void refresh()
}

