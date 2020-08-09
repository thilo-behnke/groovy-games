package org.tb.gg.config

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.Graphics

class ConfigurationExecutorFactory {
    @Inject
    private EnvironmentService environmentService

    ConfigurationExecutor getExecutor() {
       switch (environmentService.environment.graphics) {
           case Graphics.SWING:
               return new SwingConfigurationExecutor()
           default:
               throw new IllegalArgumentException("Can't enter windowed mode for unknown graphics type ${environmentService.environment.graphics}".toString())
       }
    }
}
