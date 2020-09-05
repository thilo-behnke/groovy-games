package org.tb.gg.gameObject.component.guns

import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.global.geom.Vector

class GunWheel implements Singleton {
    @Inject GunFactory gunFactory
    @Inject GameObjectProvider gameObjectProvider

    private List<Gun> guns = new ArrayList<>()
    private int currentGunIndex = -1

    Gun nextGun() {
        def newGunIndex = (currentGunIndex + 1) % guns.size()
        if (newGunIndex != currentGunIndex) {
            // Reset gun position.
            // TODO: This is not a great solution, I would rather want to disable the rendering of the gun temporarily.
            guns[currentGunIndex].body.shape.center = Vector.zeroVector()
            currentGunIndex = newGunIndex
        }
        guns.get(currentGunIndex)
    }

    Gun selectedGun() {
        guns.get(currentGunIndex)
    }

    @Override
    void init() {
        def pistol = gunFactory.create(GunType.PISTOL, new GunProperties(dim: Vector.unitVector() * 10.0, orientation: Vector.unitVector()))
        def machineGun = gunFactory.create(GunType.MACHINE_GUN, new GunProperties(dim: Vector.unitVector() * 10.0, orientation: Vector.unitVector()))
        // TODO: Should game objects just register themselves?
        gameObjectProvider << pistol
        gameObjectProvider << machineGun

        guns.add(pistol)
        guns.add(machineGun)
    }

    @Override
    void destroy() {

    }
}
