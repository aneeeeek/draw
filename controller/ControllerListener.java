package controller;

import model.Drawing;

import java.util.Stack;

public interface ControllerListener {
    default void when_undoRedo(Stack<DrawAction> undoStack, Stack<DrawAction> redoStack){

    }
    //default void when_redo(Stack<DrawAction> redoStack){

    //}
    default void when_newDrawing(Drawing drawing){

    }
}
