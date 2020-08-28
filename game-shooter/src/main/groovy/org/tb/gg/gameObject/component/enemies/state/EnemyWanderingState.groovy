package org.tb.gg.gameObject.component.enemies.state

import org.tb.gg.di.Inject
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.component.enemies.EnemyAction
import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants
import org.tb.gg.random.RandomUtilsService
import org.tb.gg.state.ActionState
import org.tb.gg.state.State

class EnemyWanderingState extends ActionState {

    @Inject
    RandomUtilsService randomUtilsService

    private Vector goal

    EnemyWanderingState(BaseGameObject parent) {
        super(EnemyState.WANDERING.toString(), parent)
    }

    @Override
    void enter() {

    }

    @Override
    State update() {
        if (!goal || isStuck()) {
            goal = randomUtilsService.getRandomPositionInWorldBounds(parent, parent.id)
            return this
        } else {
            def distanceToGoal = parent.body.center.distance(goal)
            if (distanceToGoal <= parent.body.boundingRect.diagonalLength()) {
                return new EnemyIdleState(parent)
            }
            return this
        }
    }

    private boolean isStuck() {
        parent.physicsComponent.collides
    }

    @Override
    void exit() {

    }

    @Override
    Set<String> getActions() {
        if (goal) {
            return translateDirectionToActions().collect { it.toString() }
        }
        return []
    }

    private Set<EnemyAction> translateDirectionToActions() {
        def xAxisRight = new Vector(x: 1, y: 0)
        def direction = goal - parent.body.center
        def angleBetween = xAxisRight.angleBetween(direction)

        def directionActions = []
        if (angleBetween >= MathConstants.PI * 2 || angleBetween >= 0 && angleBetween <= MathConstants.QUARTER_PI) {
            directionActions.add(EnemyAction.RIGHT)
        } else if (angleBetween >= MathConstants.HALF_PI + MathConstants.QUARTER_PI && angleBetween <= MathConstants.PI + MathConstants.HALF_PI) {
            directionActions.add(EnemyAction.LEFT)
        }

        if (angleBetween >= MathConstants.QUARTER_PI && angleBetween <= MathConstants.HALF_PI + MathConstants.QUARTER_PI) {
            directionActions.add(EnemyAction.UP)
        } else if (angleBetween >= MathConstants.PI + MathConstants.QUARTER_PI && angleBetween <= MathConstants.PI * 2 - MathConstants.QUARTER_PI) {
            directionActions.add(EnemyAction.DOWN)
        }

        return directionActions
    }
}
