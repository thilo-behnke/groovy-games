package org.tb.gg.env


import org.tb.gg.di.definition.Singleton
import org.tb.gg.env.frame.GraphicsAPIFrameProvider
import org.tb.gg.input.mouseEvent.MouseEventProvider
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.resources.ResourceLoader

interface EnvironmentService extends Singleton {
    static class GraphicsAPIEnvironment {
        RenderDestination renderDestination
        GraphicsAPIFrameProvider frameProvider
        MouseEventProvider mouseEventProvider
        ResourceLoader resourceLoader
    }

    GraphicsAPIEnvironment constructGraphicsAPIEnvironment()
    EnvironmentSettings getEnvironment()
}
