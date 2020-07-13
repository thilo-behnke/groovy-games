import engine.*
import gameObject.DefaultGameObjectProvider
import global.DefaultDateProvider
import renderer.Renderer
import renderer.destination.Canvas2dDestination
import renderer.destination.JPanelDestination
import utils.HaltingExecutorService

import javax.swing.JFrame
import javax.swing.SwingUtilities

class Main {
    static void main(String[] args) {
        def gameObjectProvider = new DefaultGameObjectProvider()
        def dateProvider = new DefaultDateProvider()
        def sceneProvider = new DefaultSceneProvider()
        def executorService = new HaltingExecutorService()
        def executionRuleEngine = new GameEngineExecutionRuleEngine()
        executionRuleEngine << ShutdownAfterFixedNumberOfCyclesExecutionRule.nrOfCycles(10000)

        def renderDestination = new JPanelDestination()
        SwingUtilities.invokeLater({
            JFrame f = new JFrame("Game");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(renderDestination);
            f.pack();
            f.setVisible(true);
        });

        def renderer = new Renderer(renderDestination: renderDestination)

        def gameEngine = new GameEngine(executorService, dateProvider, sceneProvider, renderer)
        gameEngine.setExecutionRuleEngine(executionRuleEngine)

        def defaultScene = new DefaultGameScene('default', gameObjectProvider)
        gameEngine.addScene(defaultScene)
        gameEngine.changeScene(defaultScene.name)

        gameEngine.start()
    }
}