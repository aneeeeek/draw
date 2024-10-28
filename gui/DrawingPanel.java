package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import logic.DrawingListener;
import logic.ReadOnlyVectorDrawing;
import shapes.Shape;


/// Находится на экране, слушает когда нужно перерисоваться
/// Умеет делать BufferedImage
public class DrawingPanel extends JPanel implements DrawingListener {
	final ReadOnlyVectorDrawing drawingState;

    public DrawingPanel(ReadOnlyVectorDrawing state, Dimension size) {
		drawingState = state;
        this.setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.WHITE);
    }

    public BufferedImage getImage() {
		BufferedImage bi = new BufferedImage(getPreferredSize().width,
				getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		this.print(g);
		return bi;
	}

	@Override
	public void draw() {
		repaint();
	}

    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Shape s : drawingState) {
			s.draw(g);
		}
	}
}