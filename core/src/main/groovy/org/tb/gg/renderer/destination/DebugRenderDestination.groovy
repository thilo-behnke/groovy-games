package org.tb.gg.renderer.destination

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.RenderOptions

class DebugRenderDestination implements RenderDestination {
    @Inject
    EnvironmentService environmentService

    @Override
    void setDimensions(int width, int height) {

    }

    @Override
    void drawImage(Object image, Vector topLeft, BigDecimal rotation, RenderOptions options) {

    }

    @Override
    void drawLine(Vector start, Vector end, RenderOptions options) {

    }

    @Override
    void drawPoint(Vector pos, RenderOptions options) {

    }

    @Override
    void drawCircle(Vector center, BigDecimal radius, RenderOptions options) {

    }

    @Override
    void drawRect(Vector topLeft, Vector dim, Float rotation, RenderOptions options) {

    }

    @Override
    void drawText(Vector pos, String text, RenderOptions options) {

    }

    @Override
    void refresh() {

    }

}
