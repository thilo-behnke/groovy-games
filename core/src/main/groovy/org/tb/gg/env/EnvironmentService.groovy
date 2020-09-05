package org.tb.gg.env

import groovy.util.logging.Log4j
import org.tb.gg.di.definition.Singleton
import org.tb.gg.env.frame.DefaultFrameService
import org.tb.gg.env.frame.FrameService
import org.tb.gg.renderer.destination.JPanelDestination
import org.tb.gg.renderer.destination.RenderDestination

import javax.swing.JFrame

@Log4j
class EnvironmentService implements Singleton {
    private EnvironmentSettings environment
    private EnvironmentAnalyzer environmentAnalyzer = new SystemPropertiesEnvironmentAnalyzer()

    @Override
    void init() {
        def graphics = environmentAnalyzer.getGraphics()
        setEnvironment(graphics)
    }

    @Override
    void destroy() {

    }

    private void setEnvironment(Graphics graphics) {
        if (this.environment) {
            throw new IllegalStateException("Can't redefine the environment once it was set!")
        }
        if (graphics == null) {
            graphics = Graphics.SWING
        }
        def environment = new EnvironmentSettings(graphicsAPI: graphics)
        log.info("Environment determined: " + environment)
        this.environment = environment
    }

    static class GraphicsAPIEnvironment {
        RenderDestination renderDestination
        FrameService frameService
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

    private static constructSwingEnvironment() {
        def renderDestination = new JPanelDestination()

        JFrame f = new JFrame("Game")
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        f.setResizable(false)
        f.add(renderDestination)
        f.setUndecorated(false)
        f.setVisible(true)
        f.pack()
        def frameService = new DefaultFrameService()
        frameService.setFrame(f)

        return new GraphicsAPIEnvironment(renderDestination: renderDestination, frameService: frameService)
    }

    EnvironmentSettings getEnvironment() {
        if (this.environment == null) {
            throw new IllegalStateException("Tried to access environment before it was set!")
        }
        return environment
    }
}
