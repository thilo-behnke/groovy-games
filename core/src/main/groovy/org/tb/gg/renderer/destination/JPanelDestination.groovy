package org.tb.gg.renderer.destination

import org.tb.gg.config.ConfigurationService
import org.tb.gg.di.Inject
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions

import javax.swing.BorderFactory
import javax.swing.JPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.Rectangle2D
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.util.concurrent.ConcurrentLinkedQueue

class JPanelDestination extends JPanel implements RenderDestination<BufferedImage> {
    // TODO: Alternative BufferedImage: https://stackoverflow.com/questions/43236656/jpanel-painting-not-cleared.
    private Queue<DrawAction> drawQueue = new ConcurrentLinkedQueue<>()

    @Inject
    private ConfigurationService configurationService

    class DrawAction {
        Closure action
        RenderOptions options
    }

    JPanelDestination() {
        setBorder(BorderFactory.createLineBorder(Color.black))
    }

    @Override
    void setDimensions(int width, int height) {
//        dimensions = [width, height]
    }

    @Override
    Dimension getPreferredSize() {
        if(!configurationService) {
            return new Dimension(400, 400)
        }
        def (x, y) = configurationService.getConfiguration().resolution
        return new Dimension(x, y)
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

    private static translateColor(DrawColor drawColor) {
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

    // TODO: Could be this repetition be solved with an AST transformation?
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
    void drawRect(Vector topLeft, Vector dim, Float rotation, RenderOptions options) {
        def drawCl = { Graphics2D g ->
            def rectangle = new Rectangle2D.Float(topLeft.x, getHeight() - topLeft.y, dim.x, dim.y)
            if (rotation > 0) {
                AffineTransform tx = new AffineTransform();
                tx.rotate(-rotation, rectangle.centerX, rectangle.centerY);
                rectangle = tx.createTransformedShape(rectangle)
            }

            g.draw(rectangle)
        }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void drawText(Vector pos, String text, RenderOptions options) {
        def drawCl = { Graphics2D g -> g.drawString(text, pos.x, getHeight() - pos.y) }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void drawImage(BufferedImage image, Vector topLeft, BigDecimal rotation, RenderOptions options) {
        def drawCl = { Graphics2D g ->

            if (rotation > 0) {
                AffineTransform tx = AffineTransform.getRotateInstance(-rotation, image.getWidth() / 2, image.getHeight() / 2)
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                BufferedImage rotatedImage = op.filter(image, null)

                g.drawImage(rotatedImage, topLeft.x.toInteger(), getHeight() - topLeft.y.toInteger(), null)
            } else {
                g.drawImage(image, topLeft.x.toInteger(), getHeight() - topLeft.y.toInteger(), null)
            }
        }
        drawQueue << new DrawAction(action: drawCl, options: options)
    }

    @Override
    void refresh() {
        repaint()
    }
}
