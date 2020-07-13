package gameObject.components

import gameObject.GameObject
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode
import renderer.renderObjects.Renderable

abstract class RenderComponent implements Renderable {
    GameObject parent
}
