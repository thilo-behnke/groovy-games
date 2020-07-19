package renderer.destination

import global.geom.Vector
import renderer.options.DrawColor
import renderer.options.RenderOptions

import javax.swing.BorderFactory
import javax.swing.JPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.util.concurrent.ConcurrentLinkedQueue

class JPanelDestination extends JPanel implements RenderDestination<Vector> {
    private Queue<DrawAction> drawQueue = new ConcurrentLinkedQueue<>()
    private defaultColor = Color.black

    class DrawAction {
        Closure action
        RenderOptions options
    }

    JPanelDestination() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    Dimension getPreferredSize() {
        return new Dimension(250, 200);
    }

    @Override
    void paintComponent(Graphics g) {
        super.paintComponent(g)

        drawQueue.forEach({
            g.setColor(translateColor(it.options.drawColor))
            it.action g
        })
        drawQueue.clear()
    }

    private translateColor(DrawColor drawColor) {
        switch (drawColor) {
            case DrawColor.BLACK:
                return Color.black
            case DrawColor.RED:
                return Color.red
            case DrawColor.YELLOW:
                return Color.yellow
            default:
                return Color.black
        }
    }

    @Override
    void drawLine(Vector start, Vector end, RenderOptions options) {
        def drawCl = { Graphics2D g ->
            g.setColor()
            g.draw(new Line2D.Float(start.x, start.y, end.x, end.y))
        }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void drawPoint(Vector pos, RenderOptions options) {
        def drawCl = { Graphics2D g -> g.draw(new Ellipse2D.Float(pos.x, pos.y, 1, 1)) }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void drawCircle(Vector center, BigDecimal radius, RenderOptions options) {
        def drawCl = { Graphics2D g -> g.draw(new Ellipse2D.Float(center.x, center.y, radius, radius)) }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void refresh() {
        repaint()
    }
}
