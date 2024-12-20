package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Shape;

public class Drawing implements Iterable<Shape>{

	private List<DrawingListener> listeners = new ArrayList<>();

	private Selection selection;

	private ArrayList<Shape> shapes;

	public Dimension getCanvasSize() {
		return canvasSize;
	}

	private Dimension canvasSize;

	public Drawing(Dimension size) {
		shapes = new ArrayList<>(0);
		canvasSize = size;
		selection = new Selection();
	}

	public void addListener(DrawingListener listener){
		listeners.add(listener);
		for(Shape s : shapes){
			s.addListener(listener);
		}
		selection.addListener(listener);
	}


//	public BufferedImage getImage() {
//
//		BufferedImage bi = new BufferedImage(getPreferredSize().width,
//				getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
//		Graphics g = bi.createGraphics();
//		this.print(g);
//		return bi;
//	}

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

	public void insertShape(Shape s) {
		shapes.add(s);
		listeners.forEach(listener -> s.addListener(listener));
		listeners.forEach(listener -> listener.when_addedShape(s));
	}

	@Override
	public Iterator<Shape> iterator() {
		return shapes.iterator();
	}

	public void listShapes() {
		System.out.println("---");
		for (Shape s : shapes) {
			System.out.println(s);
		}
		System.out.println("---");
	}

	public void removeShape(Shape s) {
		shapes.remove(s);
		listeners.forEach(listener -> listener.when_deletedShape(s));
		s.removeListener(listeners);
	}
	public Selection getSelection() {
		return selection;
	}
	public void selectAll() {
		selection.empty();
		for (Shape sh : this) {
			selection.add(sh);
			listeners.forEach(listener -> listener.when_selectedShape(sh));
		}
	}
}
