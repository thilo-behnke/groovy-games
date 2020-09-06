package org.tb.gg.renderer.destination

import org.tb.gg.renderer.options.DrawColor

import javax.swing.BorderFactory
import javax.swing.JPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.util.concurrent.ConcurrentLinkedQueue

class JPanelWrapper extends JPanel {
    // TODO: Alternative BufferedImage: https://stackoverflow.com/questions/43236656/jpanel-painting-not-cleared.
    private Queue<JPanelDestination.DrawAction> drawQueue = new ConcurrentLinkedQueue<>()
    private Dimension dimension

    JPanelWrapper(Dimension dimension = new Dimension(400, 400)) {
        this.dimension = dimension

        setBorder(BorderFactory.createLineBorder(Color.black))
        setPreferredSize(dimension)
    }

    void draw(JPanelDestination.DrawAction drawAction) {
        drawQueue.add(drawAction)
    }

    @Override
    Dimension getPreferredSize() {
        return dimension
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

}
