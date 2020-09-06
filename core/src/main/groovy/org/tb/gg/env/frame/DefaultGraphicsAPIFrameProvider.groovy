package org.tb.gg.env.frame

class DefaultGraphicsAPIFrameProvider implements GraphicsAPIFrameProvider {

    private Object frame

    void setFrame(Object frame) {
        this.frame = frame
    }

    Object getFrame() {
        return this.frame
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
