package gui;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.DrawingController;
import controller.Tool;
import model.Circle;
import model.FillableShape;
import model.Line;
import model.Rectangle;
import model.Shape;
import model.Text;

/**
 * MouseListener listens to the mouse events in a drawing and modifies the
 * Drawing through a DrawingController
 * 
 * @author Alex Lagerstedt
 * 
 */

public class MouseListener extends MouseAdapter {

	private DrawingController c;
	private ToolBox tools;

	boolean isDrawing;
	boolean multiSelect;

	private Point startPos;
	private Point lastPos;

	private Point mouseDelta;

	private Shape newShape;


	/**
	 * Constructs a new MouseListener
	 * 
	 * @param c
	 *            the DrawingController through which the modifications will be
	 *            done
	 * @param t
	 *            the ToolBox
	 */
	public MouseListener(DrawingController c, ToolBox t) {
		this.tools = t;
		this.c = c;
		this.newShape = null;
		this.mouseDelta = new Point(0, 0);

	}

	public void mouseDragged(MouseEvent m) {
		mouseDelta.y = m.getPoint().y - lastPos.y;
		mouseDelta.x = m.getPoint().x - lastPos.x;

		if (isDrawing && (newShape != null)) {
			newShape.setPoint2(lastPos);
		}

		if (c.getTool() == Tool.SELECT) {
			c.moveSelectedShapes(new Point(mouseDelta.x, mouseDelta.y));
		}

		lastPos = m.getPoint();

	}

	public void mouseMoved(MouseEvent m) {
		lastPos = m.getPoint();
	}

	public void mousePressed(MouseEvent m) {
		startPos = lastPos;

		Tool t = c.getTool();
		isDrawing = true;

		if (t == Tool.SELECT) {

			Shape tmp = c.getDrawing().getShapeAt(startPos);

			if (((m.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == 0)
					&& !c.getDrawing().getSelection().contains(tmp)) {
				c.getDrawing().getSelection().empty();
			}

			if ((tmp != null) && (!c.getDrawing().getSelection().contains(tmp))) {

				// empty the selection before selecting a new shape if shift is
				// not down

				tools.setColor(tmp.getColor());

				if ((c.getDrawing().getSelection().isEmpty())
						&& (tmp instanceof FillableShape)) {
					tools.setFill(((FillableShape) tmp).getFilled());
				}

				if (tmp instanceof Text) {
					tools.setFontSize(((Text) tmp).getFont().getSize());
				}

				c.getDrawing().getSelection().add(tmp);

			}


		}
		else if (t == Tool.RECTANGLE) {
			newShape = new Rectangle(startPos.x, startPos.y, tools.getFill());
		}
		else if (t == Tool.CIRCLE) {
			newShape = new Circle(startPos.x, startPos.y, tools.getFill());
		}
		else if (t == Tool.LINE) {
			newShape = new Line(startPos.x, startPos.y);
		}
		else if (t == Tool.TEXT) {
			try {
				newShape = new Text(startPos.x, startPos.y, tools.getFontSize());
			}
			catch (IllegalArgumentException e) {
			}
		}

		if (newShape != null) {
			newShape.setColor(tools.getColor());
			c.addShape(newShape);
		}

	}

	public void mouseReleased(MouseEvent m) {
		isDrawing = false;
		newShape = null;

		if (c.getTool() == Tool.SELECT) {
			Point total = new Point(m.getPoint().x - startPos.x, m.getPoint().y
					- startPos.y);

			//if ((total.x != 0) || (total.y != 0)) {
				//c.moveSelectedShapes(total);
			//}
			c.getUndoManager().getLastAction().disableMerge();
		}
	}

}
