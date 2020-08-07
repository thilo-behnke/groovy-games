package org.tb.gg.gameObject.shape


import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.Renderable

interface Shape extends Renderable {
    Vector getCenter()
    void setCenter(Vector pos)
    boolean isPointWithin(Vector pos)
}