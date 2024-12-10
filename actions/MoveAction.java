package actions;

import java.awt.Point;

import logic.Selection;
import shapes.Shape;

/**
 * MoveAction implements a single undoable action where all the Shapes in a
 * given Selection are moved.
 */
public class MoveAction implements DrawAction {

	Selection selected;
	Point movement;
	boolean isNeedMerge = true;

	/**
	 * Creates a MoveAction that moves all Shapes in the given Selection in the
	 * direction given by the point. The movement is relative to the shapes
	 * original position.
	 * 
	 * @param s
	 *            a selection which contains the shapes to be moved
	 * @param m
	 *            the amount the shapes should be moved, relative to the
	 *            original position
	 */
	public MoveAction(Selection s, Point m) {
		this.selected = s.clone();
		this.movement = m;
	}

	public void execute() {
		for (Shape s : selected) {
			s.move(movement.x, movement.y);
		}
	}

	@Override
	public DrawAction mergeActions(DrawAction other) {
		if(other instanceof MoveAction move) {
			this.movement.x += move.movement.x;
			this.movement.y += move.movement.y;
			return this;
		}

		return null;
	}

	@Override
	public boolean getIsNeedMerge() {
		return isNeedMerge;
	}

	@Override
	public void disableMerge() {
		this.isNeedMerge = false;
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		for (Shape s : selected) {
			s.move(-movement.x, -movement.y);
		}
	}

}
