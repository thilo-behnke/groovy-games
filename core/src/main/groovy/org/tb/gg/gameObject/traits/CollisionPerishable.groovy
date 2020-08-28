package org.tb.gg.gameObject.traits


import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.PerishCondition
import org.tb.gg.gameObject.Perishable

// TODO: Add ast transformation annotation to add parameters: Nr. of collisions, include / exclude collision groups.
@PerishCondition
trait CollisionPerishable implements Perishable, GameObject {
    @SuppressWarnings('unused')
    Boolean shouldPerish__CollisionPerishable() {
        physicsComponent.collides
    }
}