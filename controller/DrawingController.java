package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gui.DrawingPanel;
import model.Drawing;
import model.Selection;
import model.Shape;

public class DrawingController {

	private Drawing drawing;

	public UndoManager getUndoManager() {
		return undoManager;
	}

	private UndoManager undoManager;

	private List<ControllerListener> listeners = new ArrayList<>();

	public void addListener(ControllerListener listener){
		listeners.add(listener);
	}

	private Tool tool;
	//public DrawingPanel panel;
	private AskUser askUser;

	public DrawingController(AskUser askUser) {
		drawing = null;
		undoManager = new UndoManager();
		tool = Tool.LINE;
		this.askUser = askUser;
	}
	//public void setPanel(DrawingPanel panel){
		//this.panel=panel;
	//}

	public void export(){
		DrawIO io = new DrawIO();
		File file = askUser.askFile();
		io.export(file,this);
	}
	public void save(){
		DrawIO io = new DrawIO();
		File file = askUser.askFile();
		io.save(file,this);
	}
	public void open(){
		DrawIO io = new DrawIO();
		File file = askUser.askFile();
		io.open(file,this);
	}


	public void addShape(Shape s) {
		DrawAction add = new AddAction(drawing, s);
		add.execute();
		undoManager.addAction(add);
	}

	public void colorSelectedShapes(Color c) {
		for (Shape s : drawing.getSelection()) {
			DrawAction col = new ColorAction(s, c);
			col.execute();
			undoManager.addAction(col);
		}
	}

	public void deleteSelectedShapes() {
		DrawAction del = new DeleteAction(drawing, drawing.getSelection());
		del.execute();
		undoManager.addAction(del);
	}

	public Drawing getDrawing() {
		return drawing;
	}

	public Tool getTool() {
		return tool;
	}

	public void moveSelectedShapes(Point movement) {
		if (!drawing.getSelection().isEmpty()) {
			DrawAction move = new MoveAction(drawing.getSelection(), movement);
			move.execute();
			undoManager.addAction(move);
		}
	}

	public void newDrawing() {
		newDrawing(askUser.askDrawingSize());
	}

	public void newDrawing(Dimension size) {
		drawing = new Drawing(size);
		//panel.setDrawing(drawing);

		undoManager.clearUndoManager();
		listeners.forEach(listener -> listener.when_newDrawing(drawing));
	}

	public void redo() {
		if (this.undoManager.canRedo()) {
			this.undoManager.redo();
		}
	}

	public void setTool(Tool t) {
		this.tool = t;
	}

	public void toggleFilled() {
		DrawAction toggle = new FillAction(drawing.getSelection());
		toggle.execute();
		undoManager.addAction(toggle);
	}

	public void undo() {
		if (this.undoManager.canUndo()) {
			this.undoManager.undo();
		}
	}
}
