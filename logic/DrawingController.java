package logic;

import gui.DrawGUI;
import gui.DrawingPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import shapes.Shape;
import actions.AddAction;
import actions.ColorAction;
import actions.DeleteAction;
import actions.DrawAction;
import actions.FillAction;
import actions.MoveAction;
import actions.UndoManager;

public class DrawingController {

	private DrawingStateNotifier drawingStateNotifier;
	private DrawingPanel panel;
	private UndoManager undoManager;
	private Selection selection;
	private DrawGUI gui;
	private Tool tool;

	public DrawingController(DrawingStateNotifier stateNotifier, DrawGUI g) {
		drawingStateNotifier = stateNotifier;
		undoManager = new UndoManager();
		selection = new Selection();
		gui = g;
		tool = Tool.LINE;
	}

	public void addShape(Shape s) {
		DrawAction add = new AddAction(drawingStateNotifier, s);
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
		DrawAction del = new DeleteAction(drawingStateNotifier, selection);
		del.execute();
		undoManager.addAction(del);
		drawingStateNotifier.notifyListeners();
	}

	public Selection getSelection() {
		return selection;
	}

	public Tool getTool() {
		return tool;
	}

	public void moveSelectedShapes(Point movement, boolean needRecord) {
		if (!selection.isEmpty()) {
			DrawAction move = new MoveAction(selection, movement);
			if (needRecord) {
				undoManager.addAction(move);
			} else {
				move.execute();
			}
		}
	}

	// TODO в будущем нужно убать, сейчас нужен для совместимости
	public DrawingStateNotifier getDrawingState() {
		return drawingStateNotifier;
	}

	// TODO не должен создавать новый Drawing
	public void newDrawingPanel() {
		drawingStateNotifier.reset();
		if (gui != null) {
			gui.updateDrawing();
		}
	}

	public void redo() {
		if (this.undoManager.canRedo()) {
			this.undoManager.redo();
		}
		drawingStateNotifier.notifyListeners();
	}

	public void selectAll() {
		selection.empty();
		for (Shape sh : drawingStateNotifier) {
			selection.add(sh);
		}
		drawingStateNotifier.notifyListeners();
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
		drawingStateNotifier.notifyListeners();
	}
}
