package org.tb.gg.resources

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class SwingResourceLoader implements ResourceLoader<SwingImageWrapper> {
    private Map<String, SwingImageWrapper> images = new HashMap<>()

    @Override
    void loadResource(String path, String name) {
        def image = ImageIO.read(new File(getClass().getClassLoader().getResource(path).getFile()))
        images.put(name, new SwingImageWrapper(image))
    }

    @Override
    Optional<SwingImageWrapper> getResource(String name) {
        def resource = images.get(name)
        return Optional.ofNullable(resource)
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
