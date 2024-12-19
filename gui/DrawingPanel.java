package gui;

import logic.Drawing;
import shapes.Shape;

import javax.swing.*;
import java.awt.*;

/**
 * A simple container that contains a Drawing instance and keeps it
 * centered.
 *
 * @author Alex Lagerstedt
 *
 */

public class DrawingPanel extends JPanel {

    private Drawing drawing;

    public DrawingPanel(MouseListener mouse) {
        super(new GridBagLayout());

        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public void setDrawing(Drawing d) {
        this.drawing=d;
        this.setPreferredSize(d.getCanvasSize());
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if(drawing != null) {
            for (Shape s : drawing) {
                s.draw(g);
            }
        }
    }

}
