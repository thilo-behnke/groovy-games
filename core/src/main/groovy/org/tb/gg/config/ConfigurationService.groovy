package org.tb.gg.config

import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Singleton
import org.tb.gg.env.EnvironmentService
import org.tb.gg.renderer.destination.RenderDestination

class ConfigurationService implements Singleton {
    // TODO: Load from file somewhere.
    private static final defaultConfiguration = new ConfigurationSettings(resolution: new Tuple2(1280, 720), windowMode: ConfigurationSettings.WindowMode.WINDOWED)
    // TODO: This will change the defaultConfiguration when updated.
    @Delegate
    private ConfigurationSettings configuration = defaultConfiguration

    @Inject
    private EnvironmentService environmentService
    @Inject
    private RenderDestination renderDestination

    private ConfigurationExecutor configurationExecutor

    ConfigurationSettings getConfiguration() {
        return configuration
    }

    void setFullScreen() {
        if (isFullScreen()) {
            return
        }
        configurationExecutor.setFullScreen()
        configuration.windowMode = ConfigurationSettings.WindowMode.FULLSCREEN
    }

    void setWindowed() {
        if (isWindowed()) {
            return
        }
        configurationExecutor.setWindowed()
        configuration.windowMode = ConfigurationSettings.WindowMode.WINDOWED
    }

    boolean isFullScreen() {
        return configuration.windowMode == ConfigurationSettings.WindowMode.FULLSCREEN
    }

    boolean isWindowed() {
        return configuration.windowMode == ConfigurationSettings.WindowMode.WINDOWED
    }

    @Override
    void init() {
        configurationExecutor = new ConfigurationExecutorFactory().getExecutor()
        // TODO: It is not that easy - the panel has to be recreated when the dimensions change :/
        renderDestination.setDimensions(resolution.getV1(), resolution.getV2())
    }

    @Override
    void destroy() {

    }
}
