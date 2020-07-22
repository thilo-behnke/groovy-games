import engine.*
import global.DefaultDateProvider
import input.actions.InputActionProvider
import input.actions.InputActionRegistry
import input.keyEvent.KeyEventJwtAdapter
import renderer.DefaultRenderer
import renderer.destination.JPanelDestination
import utils.HaltingExecutorService

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
        def keyEventSubject = new KeyEventJwtAdapter(f)
        def inputActionProvider = new InputActionProvider(inputActionRegistry, keyEventSubject)

        def renderer = new DefaultRenderer(renderDestination: renderDestination)

        def gameEngine = new GameEngine(executorService, dateProvider, sceneProvider, renderer)
        gameEngine.setExecutionRuleEngine(executionRuleEngine)

        return gameEngine
    }
}