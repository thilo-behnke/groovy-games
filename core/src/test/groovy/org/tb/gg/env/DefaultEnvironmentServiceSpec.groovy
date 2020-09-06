package org.tb.gg.env

import org.tb.gg.env.systemProperty.SystemPropertyProvider
import org.tb.gg.input.awt.SwingMouseEventAdapter
import org.tb.gg.renderer.destination.JPanelDestination
import org.tb.gg.resources.SwingResourceLoader
import spock.lang.Specification

class DefaultEnvironmentServiceSpec extends Specification {

    DefaultEnvironmentService defaultEnvironmentService
    SystemPropertiesEnvironmentAnalyzer systemPropertiesEnvironmentAnalyzer

    void setup() {
        systemPropertiesEnvironmentAnalyzer = Mock(SystemPropertiesEnvironmentAnalyzer)
        defaultEnvironmentService = new DefaultEnvironmentService(systemPropertiesEnvironmentAnalyzer)
    }

    def 'should set debugMode to false if the system property is not found'() {
        when:
        systemPropertiesEnvironmentAnalyzer.isDebugModeActive() >> false
        defaultEnvironmentService.init()
        then:
        !defaultEnvironmentService.environment.debugMode
    }

    def 'should set debugMode to true if the system property is found'() {
        when:
        systemPropertiesEnvironmentAnalyzer.isDebugModeActive() >> true
        defaultEnvironmentService.init()
        then:
        defaultEnvironmentService.environment.debugMode
    }

    def 'should set graphicsAPI to Swing if the system property is set to Swing'() {
        when:
        systemPropertiesEnvironmentAnalyzer.getGraphics() >> Graphics.SWING
        defaultEnvironmentService.init()
        then:
        defaultEnvironmentService.environment.graphicsAPI == Graphics.SWING
    }

    def 'should throw an exception for an unknown graphics environment'() {
        when:
        systemPropertiesEnvironmentAnalyzer.getGraphics() >> 'UNKNOWN'
        defaultEnvironmentService.init()
        then:
        thrown(IllegalArgumentException)
    }

    def 'should throw an exception if the environment is accessed before initialization'() {
        when:
        defaultEnvironmentService.environment
        then:
        thrown(IllegalStateException)
    }

    def 'should correctly construct the swing environment'() {
        given:
        systemPropertiesEnvironmentAnalyzer.getGraphics() >> Graphics.SWING
        defaultEnvironmentService.init()
        when:
        def swingEnv = defaultEnvironmentService.constructGraphicsAPIEnvironment()
        then:
        swingEnv.renderDestination instanceof JPanelDestination && swingEnv.resourceLoader instanceof SwingResourceLoader && swingEnv.mouseEventProvider instanceof SwingMouseEventAdapter


    }
}
