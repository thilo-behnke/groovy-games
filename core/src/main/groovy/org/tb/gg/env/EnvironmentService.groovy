package org.tb.gg.env

import groovy.util.logging.Log4j
import org.tb.gg.di.definition.Singleton
import org.tb.gg.env.frame.DefaultGraphicsAPIFrameProvider
import org.tb.gg.env.frame.GraphicsAPIFrameProvider
import org.tb.gg.input.awt.SwingMouseEventAdapter
import org.tb.gg.input.mouseEvent.MouseEventProvider
import org.tb.gg.renderer.destination.DebugRenderDestination
import org.tb.gg.renderer.destination.JPanelDestination
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.resources.ResourceLoader
import org.tb.gg.resources.SwingResourceLoader

import javax.swing.JFrame

@Log4j
class EnvironmentService implements Singleton {
    private EnvironmentSettings environment
    private EnvironmentAnalyzer environmentAnalyzer = new SystemPropertiesEnvironmentAnalyzer()

    @Override
    void init() {
        def graphics = environmentAnalyzer.getGraphics()
        def debugModeActive = environmentAnalyzer.isDebugModeActive()
        setEnvironment(graphics, debugModeActive)
    }

    @Override
    void destroy() {

    }

    private void setEnvironment(Graphics graphics, boolean debugModeActive) {
        if (this.environment) {
            throw new IllegalStateException("Can't redefine the environment once it was set!")
        }
        if (graphics == null) {
            graphics = Graphics.SWING
        }
        def environment = new EnvironmentSettings(graphicsAPI: graphics, debugMode: debugModeActive)
        log.info("Environment determined: " + environment)
        this.environment = environment
    }

    static class GraphicsAPIEnvironment {
        RenderDestination renderDestination
        GraphicsAPIFrameProvider frameProvider
        MouseEventProvider mouseEventProvider
        ResourceLoader resourceLoader
    }

    GraphicsAPIEnvironment constructGraphicsAPIEnvironment() {
        switch (environment.graphicsAPI) {
            case Graphics.SWING:
                def swingEnvironment = constructSwingEnvironment()
                return swingEnvironment
            default:
                throw new IllegalArgumentException("No environment available for graphicsAPI ${environment.graphicsAPI}")
        }
    }

    private constructSwingEnvironment() {
        def renderDestination = constructSwingRenderDestination()

        // Construct frame and canvas panel.
        JFrame f = new JFrame("Game")
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        f.setResizable(false)
        f.setUndecorated(false)
        f.setVisible(true)
        f.pack()
        def frameService = new DefaultGraphicsAPIFrameProvider()
        frameService.setFrame(f)

        def mouseEventProvider = new SwingMouseEventAdapter()
        def resourceLoader = new SwingResourceLoader()

        return new GraphicsAPIEnvironment(renderDestination: renderDestination, frameProvider: frameService, mouseEventProvider: mouseEventProvider, resourceLoader: resourceLoader)
    }

    private constructSwingRenderDestination() {
        def renderDestination = new JPanelDestination()
        if (!environment.debugMode) {
            return renderDestination
        }
        return new DebugRenderDestination(renderDestination)
    }

    EnvironmentSettings getEnvironment() {
        if (this.environment == null) {
            throw new IllegalStateException("Tried to access environment before it was set!")
        }
        return environment
    }
}
