package logic;

import java.awt.Point;

import shapes.Shape;


public interface ReadOnlyVectorDrawing extends Iterable<Shape> {
	public Shape getShapeAt(Point p);

	public int nShapes();

	public void insertShape(Shape s);

	public void listShapes() ;
}

