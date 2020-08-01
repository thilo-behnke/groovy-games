package org.tb.gg.config

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.Graphics

import javax.swing.JFrame
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment

class SwingConfigurationExecutor implements ConfigurationExecutor {
    @Inject
    private EnvironmentService environmentService

    @Override
    void setFullScreen() {
        switch(environmentService.environment.graphics) {
            case Graphics.SWING:
                def jFrame = (JFrame) environmentService.environment.environmentFrame
                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice device = env.getDefaultScreenDevice();

                jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH)
                device.setFullScreenWindow(jFrame)
                break
            default:
                throw new IllegalArgumentException("Can't enter fullscreen mode for unknown graphics type ${environmentService.environment.graphics}".toString())
        }
    }

    @Override
    void setWindowed() {
        switch(environmentService.environment.graphics) {
            case Graphics.SWING:
                def jFrame = (JFrame) environmentService.environment.environmentFrame
                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice device = env.getDefaultScreenDevice();

                jFrame.setExtendedState(JFrame.NORMAL)
                device.setFullScreenWindow(null)
                jFrame.setVisible(true)
                break
            default:
                throw new IllegalArgumentException("Can't enter windowed mode for unknown graphics type ${environmentService.environment.graphics}".toString())
        }
    }
}
