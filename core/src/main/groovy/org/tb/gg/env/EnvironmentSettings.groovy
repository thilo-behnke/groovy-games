package org.tb.gg.env

import groovy.transform.Immutable
import groovy.transform.ToString
import org.tb.gg.renderer.destination.RenderDestination

@ToString
class EnvironmentSettings {
    Graphics graphicsAPI
    boolean debugMode
}

