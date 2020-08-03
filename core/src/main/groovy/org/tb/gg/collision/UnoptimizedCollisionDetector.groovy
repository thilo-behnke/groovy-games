package org.tb.gg.collision

import org.tb.gg.gameObject.GameObject

class UnoptimizedCollisionDetector implements CollisionDetector {
    @Override
    Set<Collision> detect(Set<GameObject> gameObjects) {
        def combinations = GroovyCollections.combinations((Iterable) gameObjects)
        combinations
                .collect { GameObject a, GameObject b ->
                    if (a.physicsComponent.collidesWith(b.physicsComponent.getStructure())) {
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
