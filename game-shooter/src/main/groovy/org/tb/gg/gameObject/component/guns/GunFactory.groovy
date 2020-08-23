package org.tb.gg.gameObject.component.guns

import org.tb.gg.di.definition.Singleton

class GunFactory implements Singleton {
    Gun create(GunType gunType, GunProperties gunProperties) {
       switch (gunType) {
           case GunType.PISTOL:
               return AutomaticGun.create(AutomaticGun, AutomaticGun.PISTOL_PROPERTIES, gunProperties)
           case GunType.MACHINE_GUN:
               return AutomaticGun.create(AutomaticGun, AutomaticGun.MACHINE_GUN_PROPERTIES, gunProperties)
       }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}

enum GunType {
    PISTOL, MACHINE_GUN
}
