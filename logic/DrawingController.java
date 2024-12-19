package logic;

import gui.DrawGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import gui.DrawingPanel;
import shapes.Shape;
import actions.AddAction;
import actions.ColorAction;
import actions.DeleteAction;
import actions.DrawAction;
import actions.FillAction;
import actions.MoveAction;
import actions.UndoManager;

public class DrawingController {

	private Drawing drawing;

	public UndoManager getUndoManager() {
		return undoManager;
	}

	private UndoManager undoManager;
	private Selection selection;
	private Tool tool;
	public DrawingPanel panel;

	public DrawingController() {
		drawing = null;
		undoManager = new UndoManager();
		selection = new Selection();
		tool = Tool.LINE;
	}
	public void setPanel(DrawingPanel panel){
		this.panel=panel;
	}

	public void addShape(Shape s) {
		DrawAction add = new AddAction(drawing, s);
		add.execute();
		undoManager.addAction(add);

	}

	public void colorSelectedShapes(Color c) {
		for (Shape s : selection) {
			DrawAction col = new ColorAction(s, c);
			col.execute();
			undoManager.addAction(col);
		}
	}

	public void deleteSelectedShapes() {
		DrawAction del = new DeleteAction(drawing, selection);
		del.execute();
		undoManager.addAction(del);
		panel.repaint();
	}

	public Drawing getDrawing() {
		return drawing;
	}

	public Selection getSelection() {
		return selection;
	}

	public Tool getTool() {
		return tool;
	}

	public void moveSelectedShapes(Point movement) {
		if (!selection.isEmpty()) {
			DrawAction move = new MoveAction(selection, movement);
			move.execute();
			undoManager.addAction(move);
		}
	}

	public void newDrawing(Dimension size) {
		drawing = new Drawing(size);
		//if (gui != null) {
			//gui.updateDrawing();
		//}
		panel.setDrawing(drawing);
	}

	public void redo() {
		if (this.undoManager.canRedo()) {
			this.undoManager.redo();
		}
		panel.repaint();
	}

	public void selectAll() {
		selection.empty();
		for (Shape sh : drawing) {
			selection.add(sh);
		}
		panel.repaint();

	}

	public void setTool(Tool t) {
		this.tool = t;
	}

	public void toggleFilled() {
		DrawAction toggle = new FillAction(selection);
		toggle.execute();
		undoManager.addAction(toggle);
	}

	public void undo() {
		if (this.undoManager.canUndo()) {
			this.undoManager.undo();
		}
		panel.repaint();
	}
}
