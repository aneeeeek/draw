package gui;

import controller.ControllerListener;
import model.Drawing;
import model.DrawingListener;
import model.Shape;

import javax.swing.*;
import java.awt.*;

/**
 * A simple container that contains a Drawing instance and keeps it
 * centered.
 *
 * @author Alex Lagerstedt
 *
 */

public class DrawingPanel extends JPanel implements DrawingListener, ControllerListener {

    private Drawing drawing;

    public DrawingPanel(MouseListener mouse) {
        super(new GridBagLayout());

        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    private void setDrawing(Drawing d) {
        this.drawing=d;
        this.setPreferredSize(d.getCanvasSize());

        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setSize(getPreferredSize());

        drawing.addListener(this);
        //drawing.getSelection().addListener(this);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if(drawing != null) {
            for (Shape s : drawing) {
                s.draw(g);
            }
        }
    }

    @Override
    public void when_addedShape(Shape shape) {
        repaint();
    }

    @Override
    public void when_deletedShape(Shape shape) {
        repaint();
    }

    @Override
    public void when_editedShape(Shape shape) {
        repaint();
    }

    @Override
    public void when_selectedShape(Shape shape) {
        repaint();
    }

    @Override
    public void when_deselectedShape(Shape shape) {
        repaint();
    }

    @Override
    public void when_newDrawing(Drawing drawing) {
        this.setDrawing(drawing);
        repaint();
    }
}
