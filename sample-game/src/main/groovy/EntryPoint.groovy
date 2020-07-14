import engine.DefaultGameScene
import engine.DefaultSceneProvider
import engine.GameEngine
import engine.GameEngineExecutionRuleEngine
import engine.ShutdownAfterFixedNumberOfCyclesExecutionRule
import gameObject.GameObject
import gameObject.GameObjectProvider
import gameObject.components.RenderComponent
import global.DefaultDateProvider
import global.geom.FVector
import renderer.DefaultRenderer
import renderer.destination.JPanelDestination
import renderer.renderObjects.RenderNode
import renderer.shape.Line
import utils.HaltingExecutorService

import javax.swing.JFrame

def gameObjectProvider = new GameObjectProvider()
def dateProvider = new DefaultDateProvider()
def sceneProvider = new DefaultSceneProvider()
def executorService = new HaltingExecutorService()
def executionRuleEngine = new GameEngineExecutionRuleEngine()
executionRuleEngine << ShutdownAfterFixedNumberOfCyclesExecutionRule.nrOfCycles(10000)

def renderDestination = new JPanelDestination()
// TODO: Move this away from here - shouldn't be that concrete.
//        SwingUtilities.invokeLater({
JFrame f = new JFrame("Game");
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
f.add(renderDestination);
f.pack();
f.setVisible(true);
//        });

def renderer = new DefaultRenderer(renderDestination: renderDestination)

def gameEngine = new GameEngine(executorService, dateProvider, sceneProvider, renderer)
gameEngine.setExecutionRuleEngine(executionRuleEngine)

def renderNode = RenderNode.leaf(new Line(new FVector(x: 0, y: 0), new FVector(x: 100, y: 100)))
def gameObjectRenderComponent = new RenderComponent(renderNode: renderNode)
def gameObject = new GameObject(id: 1235L, renderComponent: gameObjectRenderComponent)
gameObject.setRenderComponent(gameObjectRenderComponent)
gameObjectProvider << gameObject
def defaultScene = new DefaultGameScene('default', gameObjectProvider)
gameEngine.addScene(defaultScene)
gameEngine.changeScene(defaultScene.name)

gameEngine.start()
