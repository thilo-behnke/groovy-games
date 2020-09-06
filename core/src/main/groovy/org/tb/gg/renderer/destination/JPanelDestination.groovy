package org.tb.gg.renderer.destination

import org.tb.gg.di.Inject
import org.tb.gg.env.frame.GraphicsAPIFrameProvider
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.RenderOptions

import javax.swing.JFrame
import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.Rectangle2D
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

class JPanelDestination implements RenderDestination<BufferedImage> {
    @Inject
    GraphicsAPIFrameProvider frameService

    JPanelWrapper jPanelWrapper
    private Dimension dimension

    class DrawAction {
        Closure action
        RenderOptions options
    }

    JPanelDestination() {
        jPanelWrapper = new JPanelWrapper()
    }

    @Override
    void setDimensions(int width, int height) {
        dimension = new Dimension(width, height)

        JFrame jFrame = (JFrame) frameService.getFrame()
        jFrame.remove(jPanelWrapper)

        jPanelWrapper = new JPanelWrapper(dimension)
        jFrame.add(jPanelWrapper)
        jFrame.pack()
    }

    // TODO: Could be this repetition be solved with an AST transformation?
    @Override
    void drawLine(Vector start, Vector end, RenderOptions options) {
        def drawCl = { Graphics2D g ->
            g.setColor()
            g.draw(new Line2D.Float(start.x, jPanelWrapper.getHeight() - start.y, end.x, jPanelWrapper.getHeight() - end.y))
        }
        jPanelWrapper.draw(new DrawAction(action: drawCl, options: options))
    }

    @Override
    void drawPoint(Vector pos, RenderOptions options) {
        def drawCl = { Graphics2D g -> g.draw(new Ellipse2D.Float(pos.x, jPanelWrapper.getHeight() - pos.y, 1, 1)) }
        jPanelWrapper.draw(new DrawAction(action: drawCl, options: options))
    }

    @Override
    void drawCircle(Vector center, BigDecimal radius, RenderOptions options) {
        // TODO: Inefficient, should the shape classes provide both center and bounding areas?
        def drawCl = { Graphics2D g -> g.draw(new Ellipse2D.Float(center.x - radius, jPanelWrapper.getHeight() - center.y - radius, 2 * radius, 2 * radius)) }
        jPanelWrapper.draw(new DrawAction(action: drawCl, options: options))
    }

    @Override
    void drawRect(Vector topLeft, Vector dim, Float rotation, RenderOptions options) {
        def drawCl = { Graphics2D g ->
            def rectangle = new Rectangle2D.Float(topLeft.x, jPanelWrapper.getHeight() - topLeft.y, dim.x, dim.y)
            if (rotation > 0) {
                AffineTransform tx = new AffineTransform();
                tx.rotate(-rotation, rectangle.centerX, rectangle.centerY);
                rectangle = tx.createTransformedShape(rectangle)
            }

            g.draw(rectangle)
        }
        jPanelWrapper.draw(new DrawAction(action: drawCl, options: options))
    }

    @Override
    void drawText(Vector pos, String text, RenderOptions options) {
        def drawCl = { Graphics2D g -> g.drawString(text, pos.x, jPanelWrapper.getHeight() - pos.y) }
        jPanelWrapper.draw(new DrawAction(action: drawCl, options: options))
    }

    @Override
    void drawImage(BufferedImage image, Vector topLeft, BigDecimal rotation, RenderOptions options) {
        def drawCl = { Graphics2D g ->

            if (rotation > 0) {
                AffineTransform tx = AffineTransform.getRotateInstance(-rotation, image.getWidth() / 2, image.getHeight() / 2)
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                BufferedImage rotatedImage = op.filter(image, null)

                g.drawImage(rotatedImage, topLeft.x.toInteger(), jPanelWrapper.getHeight() - topLeft.y.toInteger(), null)
            } else {
                g.drawImage(image, topLeft.x.toInteger(), jPanelWrapper.getHeight() - topLeft.y.toInteger(), null)
            }
        }
        jPanelWrapper.draw(new DrawAction(action: drawCl, options: options))
    }

    @Override
    void refresh() {
        jPanelWrapper.repaint()
    }

    @Override
    void init() {
    }

    @Override
    void destroy() {

    }
}
