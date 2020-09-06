package org.tb.gg.renderer.destination


import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.BaseImage
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.resources.ImageWrapper

class DebugRenderDestination implements RenderDestination<ImageWrapper<BaseImage>> {
    @Delegate
    RenderDestination renderDestination

    DebugRenderDestination(RenderDestination delegate) {
        this.renderDestination = delegate
    }

    @Override
    void drawImage(ImageWrapper<BaseImage> image, Vector topLeft, BigDecimal rotation, RenderOptions options) {
        renderDestination.drawImage(image, topLeft, rotation, options)
        renderDestination.drawRect(topLeft, new Vector(x: image.getWidth(), y: image.getHeight()), rotation, new RenderOptions(drawColor: DrawColor.RED))
    }
}
