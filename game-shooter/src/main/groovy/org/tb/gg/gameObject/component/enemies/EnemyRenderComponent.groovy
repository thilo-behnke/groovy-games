package org.tb.gg.gameObject.component.enemies

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.gameObject.component.enemies.state.EnemyState
import org.tb.gg.gameObject.component.enemies.state.EnemyWanderingState
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.state.StateMachine

class EnemyRenderComponent extends RenderComponent {
    @Inject
    EnvironmentService environmentService

    @Override
    RenderNode getRenderNode() {
        EnemyGameObject enemyGameObject = (EnemyGameObject) parent;
        def debugNodes = getDebugNodes(enemyGameObject)
        return RenderNode.node(
                [
                        *debugNodes,
                        RenderNode.leaf(
                                enemyGameObject.body,
                                new RenderOptions(drawColor: enemyGameObject.wasHitRecently ? DrawColor.RED : DrawColor.BLACK)
                        )

                ],
        )
    }

    private List<RenderNode> getDebugNodes(EnemyGameObject enemyGameObject) {
        if (environmentService.environment.debugMode) {
            StateMachine enemyStateMachine = ((EnemyInputComponent) enemyGameObject.inputComponent).stateMachine
            if (enemyStateMachine.activeState.name == EnemyState.WANDERING.name()) {
                def wanderingState = (EnemyWanderingState) enemyStateMachine.activeState
                if (!wanderingState.goal) {
                    return []
                }
                def goalNode = new Circle(center: wanderingState.goal, radius: 20.0)
                def lineToGoalNode = new Line(enemyGameObject.body.center, wanderingState.goal)
                return [RenderNode.leaf(goalNode), RenderNode.leaf(lineToGoalNode)]
            }
        }
        return []
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
