package org.tb.gg.gameObject.traits

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionRegistry
import org.tb.gg.di.Inject
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