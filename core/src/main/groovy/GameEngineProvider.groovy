import engine.*
import global.DefaultDateProvider
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

        def renderDestination = new JPanelDestination()
        // TODO: Move this away from here - shouldn't be that concrete.
        JFrame f = new JFrame("Game");
        f.addKeyListener()
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(renderDestination);
        f.pack();
        f.setVisible(true);

        def renderer = new DefaultRenderer(renderDestination: renderDestination)

        def gameEngine = new GameEngine(executorService, dateProvider, sceneProvider, renderer)
        gameEngine.setExecutionRuleEngine(executionRuleEngine)

        return gameEngine
    }
}