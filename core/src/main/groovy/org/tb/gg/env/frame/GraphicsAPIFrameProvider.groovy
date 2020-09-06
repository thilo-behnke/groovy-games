package org.tb.gg.env.frame

import org.tb.gg.di.definition.Singleton

interface GraphicsAPIFrameProvider extends Singleton {
    Object getFrame()
    void setFrame(Object frame)
}
