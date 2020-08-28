package org.tb.gg.resources

import java.awt.image.BufferedImage

class SwingImageWrapper implements ImageWrapper {
    BufferedImage image

    SwingImageWrapper(BufferedImage image) {
        this.image = image
    }

    @Override
    int getWidth() {
        return image.getWidth()
    }

    @Override
    int getHeight() {
        return image.getHeight()
    }
}
