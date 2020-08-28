package org.tb.gg.resources

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class SwingResourceLoader implements ResourceLoader<BufferedImage> {
    private Map<String, BufferedImage> images = new HashMap<>()

    @Override
    void loadResource(String path, String name) {
        def image = ImageIO.read(new File(getClass().getClassLoader().getResource(path).getFile()))
        images.put(name, image)
    }

    @Override
    Optional<BufferedImage> getResource(String name) {
        Optional.ofNullable(images.get(name))
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
