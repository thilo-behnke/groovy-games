package org.tb.gg.env

class SystemPropertiesEnvironmentAnalyzer implements EnvironmentAnalyzer {
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
}
