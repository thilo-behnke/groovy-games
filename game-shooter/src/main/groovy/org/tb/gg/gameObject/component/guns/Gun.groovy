package org.tb.gg.gameObject.component.guns

import groovy.transform.Immutable
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector

trait Gun<T> implements GameObject {
    T props

    abstract void shoot();
    static Gun create(Class<? extends Gun> clazz, T specificProps, GunProperties gunProps) {
        def gun = (Gun) new GameObjectBuilder<>(clazz)
                .setBody(new ShapeBody(new Rect(gunProps.pos, new Vector(x: 10, y: 10))))
                .build()
        gun.setOrientation(gunProps.orientation)
        gun.props = specificProps
        return gun
    }
}

class GunProperties {
    Vector pos
    Vector orientation
}