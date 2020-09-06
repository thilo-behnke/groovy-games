package org.tb.gg.env

import org.tb.gg.env.systemProperty.SystemPropertyProvider

class SystemPropertiesEnvironmentAnalyzer implements EnvironmentAnalyzer {

    SystemPropertyProvider systemPropertyProvider

    SystemPropertiesEnvironmentAnalyzer(SystemPropertyProvider systemPropertyProvider) {
        this.systemPropertyProvider = systemPropertyProvider
    }

    @Override
    Graphics getGraphics() {
        def graphics = System.properties.getProperty('org.tb.gg.graphicsAPI')
        if (graphics == null) {
            return null
        }
        def graphicsValue = Graphics.valueOf(graphics)
        if (graphics == null) {
            return null
        }
        return graphicsValue
    }

    @Override
    boolean isDebugModeActive() {
        def debugMode = System.properties.getProperty('org.tb.gg.debugMode')
        return debugMode
    }
}
