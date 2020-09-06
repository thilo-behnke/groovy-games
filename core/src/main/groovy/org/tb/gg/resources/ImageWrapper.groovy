package org.tb.gg.resources

import org.tb.gg.renderer.BaseImage

interface ImageWrapper<T> extends BaseImage {
     int getWidth()
     int getHeight()
     T getImage()
}
