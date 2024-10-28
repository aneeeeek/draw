package logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import shapes.Shape;


public class DrawingStateNotifier extends DrawingNotifier implements ReadOnlyVectorDrawing, ModifyVectorDrawing {

	private ArrayList<Shape> shapes;

	public DrawingStateNotifier() {
		shapes = new ArrayList<Shape>(0);
	}

	@Override
	public Shape getShapeAt(Point p) {
		int index = shapes.size() - 1;
		while (index >= 0) {

			if (shapes.get(index).includes(p)) {
				return shapes.get(index);
			}
			index--;
		}
		return null;
	}

	@Override
	public int nShapes() {
		return shapes.size();
	}

	@Override
	public void insertShape(Shape s) {
		shapes.add(s);
	}

	@Override
	public Iterator<Shape> iterator() {
		return shapes.iterator();
	}

	@Override
	public void listShapes() {
		System.out.println("---");
		for (Shape s : shapes) {
			System.out.println(s);
		}
		System.out.println("---");
	}

	@Override
	public void lower(Shape s) {
		int index = shapes.indexOf(s);
		if (index < shapes.size() - 1) {
			shapes.remove(s);
			shapes.add(index, s);
		}
	}

	@Override
	public void raise(Shape s) {
		int index = shapes.indexOf(s);
		if (index > 0) {
			shapes.remove(s);
			shapes.add(--index, s);
		}
	}

	@Override
	public void removeShape(Shape s) {
		shapes.remove(s);
	}

	public void reset() {
		removeAllListeners();
		shapes.clear();
	}
}
