package org.tb.gg.env

import org.tb.gg.di.definition.Singleton

// TODO: Singleton service - but now for given interface.
class SystemPropertiesEnvironmentAnalyzer implements EnvironmentAnalyzer {
    @Override
    Graphics getGraphics() {
        def graphics = System.properties.getProperty('org.tb.gg.graphics')
        if (graphics == null) {
            return null
        }
        def graphicsValue = Graphics.valueOf(graphics)
        if (graphics == null) {
            return null
        }
        return graphicsValue
    }
}
