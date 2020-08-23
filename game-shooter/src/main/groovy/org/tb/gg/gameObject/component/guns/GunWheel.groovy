package org.tb.gg.gameObject.component.guns

import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Singleton
import org.tb.gg.global.geom.Vector

class GunWheel implements Singleton {
    @Inject GunFactory gunFactory

    private List<Gun> guns = new ArrayList<>()
    private int currentGunIndex = -1

    Gun nextGun() {
        currentGunIndex = (currentGunIndex + 1) % guns.size()
        guns.get(currentGunIndex)
    }

    Gun selectedGun() {
        guns.get(currentGunIndex)
    }

    @Override
    void init() {
        guns.add(gunFactory.create(GunType.PISTOL, new GunProperties(pos: Vector.unitVector(), orientation: Vector.unitVector())))
        guns.add(gunFactory.create(GunType.MACHINE_GUN, new GunProperties(pos: Vector.unitVector(), orientation: Vector.unitVector())))
    }

    @Override
    void destroy() {

    }
}
