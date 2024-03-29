package org.tb.gg.config

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.Graphics
import org.tb.gg.env.frame.GraphicsAPIFrameProvider

import javax.swing.JFrame
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment

class SwingConfigurationExecutor implements ConfigurationExecutor {
    @Inject
    private EnvironmentService environmentService
    @Inject
    private GraphicsAPIFrameProvider frameService

    @Override
    void setFullScreen() {
        switch(environmentService.environment.graphicsAPI) {
            case Graphics.SWING:
                def jFrame = (JFrame) frameService.getFrame()
                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice device = env.getDefaultScreenDevice();

                // TODO: It would be nice to also undecorate the frame, but this would mean creating a new one...
                jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH)
                device.setFullScreenWindow(jFrame)
                break
            default:
                throw new IllegalArgumentException("Can't enter fullscreen mode for unknown graphics type ${environmentService.environment.graphicsAPI}".toString())
        }
    }

    @Override
    void setWindowed() {
        switch(environmentService.environment.graphicsAPI) {
            case Graphics.SWING:
                def jFrame = (JFrame) frameService.getFrame()
                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice device = env.getDefaultScreenDevice();

                jFrame.setExtendedState(JFrame.NORMAL)
                device.setFullScreenWindow(null)
                jFrame.setVisible(true)
                break
            default:
                throw new IllegalArgumentException("Can't enter windowed mode for unknown graphics type ${environmentService.environment.graphicsAPI}".toString())
        }
    }
}
