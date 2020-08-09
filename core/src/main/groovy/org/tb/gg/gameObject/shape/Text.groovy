package org.tb.gg.gameObject.shape


import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Text extends Rect {
    String text

    // TODO: Can the text get the info of its dimensions implicitly from the renderer?
    Text(Vector topLeft, Vector dim, String text) {
        super(topLeft, dim)
        this.text = text
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawText(topLeft, text, options)
    }
}
