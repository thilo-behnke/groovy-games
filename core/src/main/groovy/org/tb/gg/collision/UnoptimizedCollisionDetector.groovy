package org.tb.gg.collision

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.tb.gg.gameObject.GameObject

class UnoptimizedCollisionDetector implements CollisionDetector {
    @Override
    Set<Collision> detect(Set<GameObject> gameObjects) {
        if (gameObjects.size() < 2) {
            return []
        }
        // TODO: Permutations for each object with each object (beware of duplicates).
        def combinations = []
        combinations
                .collect { GameObject a, GameObject b ->
                    if (a.physicsComponent?.collidesWith(b.physicsComponent?.getStructure())) {
                        return new Collision(a: a, b: b)
                    }
                    return null
                }
                .findAll { it }
                .collect { (Collision) it }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
