package renderer.destination

import global.geom.Vector

interface RenderDestination<T extends Vector> {
    void drawLine(T start, T end)

    void refresh()
}

