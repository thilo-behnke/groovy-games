package org.tb.gg.renderer.renderObjects

import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

interface Renderable {
    void render(RenderDestination renderDestination, RenderOptions options)
}