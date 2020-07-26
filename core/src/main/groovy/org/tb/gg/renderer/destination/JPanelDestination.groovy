package org.tb.gg.renderer.destination

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions

import javax.swing.BorderFactory
import javax.swing.JPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.util.concurrent.ConcurrentLinkedQueue

class JPanelDestination extends JPanel implements RenderDestination {
    private Queue<DrawAction> drawQueue = new ConcurrentLinkedQueue<>()
    private defaultColor = Color.black

    private List<Integer> dimensions = [500, 500]

    class DrawAction {
        Closure action
        RenderOptions options
    }

    JPanelDestination() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    void setDimensions(int width, int height) {
        dimensions = [width, height]
    }

    @Override
    Dimension getPreferredSize() {
        return new Dimension(dimensions[0], dimensions[1])
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
            case DrawColor.BLUE:
                return Color.blue
            case DrawColor.GREEN:
                return Color.green
            default:
                return Color.black
        }
    }

    @Override
    void drawLine(Vector start, Vector end, RenderOptions options) {
        def drawCl = { Graphics2D g ->
            g.setColor()
            g.draw(new Line2D.Float(start.x, getHeight() - start.y, end.x, getHeight() - end.y))
        }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void drawPoint(Vector pos, RenderOptions options) {
        def drawCl = { Graphics2D g -> g.draw(new Ellipse2D.Float(pos.x, getHeight() - pos.y, 1, 1)) }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void drawCircle(Vector center, BigDecimal radius, RenderOptions options) {
        // TODO: Inefficient, should the shape classes provide both center and bounding areas?
        def drawCl = { Graphics2D g -> g.draw(new Ellipse2D.Float(center.x - radius, getHeight() - center.y - radius, 2 * radius, 2 * radius)) }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void drawText(Vector pos, String text, RenderOptions options) {
        def drawCl = { Graphics2D g -> g.drawString(text, pos.x, getHeight() - pos.y) }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void refresh() {
        repaint()
    }
}
