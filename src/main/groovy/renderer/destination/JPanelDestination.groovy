package renderer.destination

import global.geom.FVector
import global.geom.Vector
import renderer.renderObjects.Renderable

import javax.swing.BorderFactory
import javax.swing.JPanel
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics

class JPanelDestination extends JPanel implements RenderDestination<FVector> {
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

        g.drawString("This is my custom Panel!",10,20);
    }

    @Override
    void draw(Renderable renderable) {

    }

    @Override
    void drawLine(FVector start, FVector end) {

    }
}
