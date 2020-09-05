package org.tb.gg.env.frame

class DefaultFrameService implements FrameService {

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
