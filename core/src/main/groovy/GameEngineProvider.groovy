import org.tb.gg.engine.*
import org.tb.gg.global.DefaultDateProvider
import org.tb.gg.input.actions.InputActionProvider
import org.tb.gg.input.actions.InputActionRegistry
import org.tb.gg.input.awt.KeyEventAwtAdapter
import org.tb.gg.renderer.DefaultRenderer
import org.tb.gg.renderer.destination.JPanelDestination
import org.tb.gg.utils.HaltingExecutorService

import javax.swing.*

class GameEngineProvider {
    // TODO: It would be great to have something here like in Spring, e.g. auto detect files with ending Scene and startup the game that way.
    static GameEngine provideGameEngine() {
        def dateProvider = new DefaultDateProvider()
        def sceneProvider = new DefaultSceneProvider()
        def executorService = new HaltingExecutorService()
        def executionRuleEngine = new GameEngineExecutionRuleEngine()
//        executionRuleEngine << ShutdownAfterFixedNumberOfCyclesExecutionRule.nrOfCycles(10000)

        // TODO: Move this away from here - shouldn't be that concrete.
        def renderDestination = new JPanelDestination()
        JFrame f = new JFrame("Game");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(renderDestination);
        f.pack();
        f.setVisible(true);

        // TODO: This should happen somewhere else, as multiple instances could exist...
        def inputActionRegistry = new InputActionRegistry()
        def keyEventSubject = new KeyEventAwtAdapter(f)
        def inputActionProvider = new InputActionProvider(inputActionRegistry, keyEventSubject)

        def renderer = new DefaultRenderer(renderDestination: renderDestination)

        def gameEngine = new GameEngine(executorService, dateProvider, sceneProvider, renderer)
        gameEngine.setExecutionRuleEngine(executionRuleEngine)

        return gameEngine
    }
}