package org.tb.gg.gameObject.component.guns


import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.components.render.DefaultRenderComponent
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector

trait Gun<T extends GunSpecificProps> implements GameObject {
    T props

    abstract void shoot();
    static Gun create(Class<? extends Gun> clazz, T specificProps, GunProperties gunProps) {
        def gun = (Gun) new GameObjectBuilder<>(clazz)
                .setRenderComponent(new DefaultRenderComponent())
                .setBody(new ShapeBody(new Rect(Vector.zeroVector(), gunProps.dim)))
                .build()
        gun.setOrientation(gunProps.orientation)
        gun.props = specificProps
        return gun
    }
}

class GunProperties {
    Vector dim
    Vector orientation
}

class GunSpecificProps {
    GunType gunType
}