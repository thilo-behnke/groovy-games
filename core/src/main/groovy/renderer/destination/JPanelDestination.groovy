package renderer.destination

import global.geom.Vector

import javax.swing.BorderFactory
import javax.swing.JPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Line2D
import java.util.concurrent.ConcurrentLinkedQueue

class JPanelDestination extends JPanel implements RenderDestination<Vector> {
    private Queue<Closure> drawQueue = new ConcurrentLinkedQueue<>()

    JPanelDestination() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    @Override
    void paintComponent(Graphics g) {
        super.paintComponent(g)

        drawQueue.forEach({it(g)})
        drawQueue.clear()
    }

    @Override
    void drawLine(Vector start, Vector end) {
        def drawCl = {Graphics2D g -> g.draw(new Line2D.Float(start.x, start.y, end.x, end.y))}
        drawQueue << drawCl
    }

    @Override
    void refresh() {
        repaint()
    }
}
