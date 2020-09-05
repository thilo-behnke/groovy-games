package org.tb.gg.env.frame

import org.tb.gg.di.definition.Singleton

interface FrameService extends Singleton {
    Object getFrame()
    void setFrame(Object frame)
}
