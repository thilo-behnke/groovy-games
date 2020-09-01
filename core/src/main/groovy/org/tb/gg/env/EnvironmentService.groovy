package org.tb.gg.env

import groovy.util.logging.Log4j
import org.tb.gg.config.ConfigurationService
import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Singleton
import org.tb.gg.renderer.destination.JPanelDestination

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
        def environment = constructEnvironment(graphics)
        log.info("Environment determined: " + environment)
        this.environment = environment
    }

    private constructEnvironment(Graphics graphics) {
        switch (graphics) {
            case Graphics.SWING:
                def swingEnvironment = this.constructSwingEnvironment()
                return new EnvironmentSettings(graphics: graphics, environmentFrame: swingEnvironment.jFrame, renderDestination: swingEnvironment.renderDestination)
            default:
                throw new IllegalArgumentException("Can't construct environment for unknown graphics type ${graphics}".toString())
        }

    }

    // TODO: Instead of providing the whole env in one object, services could also be configured through the coreConfig.groovy file.
    private constructSwingEnvironment() {
        def renderDestination = new JPanelDestination()

        JFrame f = new JFrame("Game")
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        f.setResizable(false)
        f.add(renderDestination)
        f.setUndecorated(false)
        f.setVisible(true)
        f.pack()

        return [ renderDestination: renderDestination, jFrame: f ]
    }

    EnvironmentSettings getEnvironment() {
        if (this.environment == null) {
            throw new IllegalStateException("Tried to access environment before it was set!")
        }
        return environment
    }
}
