package controller;

import model.Selection;
import model.FillableShape;
import model.Shape;

/**
 * FillAction implements a undoable action where the fill status of all the
 * Shapes in a given Selection are toggled.
 */
public class FillAction implements DrawAction {

	Selection selected;

	/**
	 * Creates a FillAction that filps the fill status of all FillableShape
	 * instances in the given selection.
	 * 
	 * @param s
	 *            a selection which contains the shapes to be modified
	 */
	public FillAction(Selection s) {
		this.selected = s.clone();
	}

	public void execute() {
		for (Shape s : selected) {
			if (s instanceof FillableShape) {
				FillableShape fs = (FillableShape) s;
				fs.setFilled(!(fs).getFilled());
			}
		}
	}

	@Override
	public DrawAction mergeActions(DrawAction other) {
		return null;
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		execute();
	}

}
