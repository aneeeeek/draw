package model;

public interface DrawingListener {
    default void when_addedShape(Shape shape){

    }
    default void when_deletedShape(Shape shape) {

    }
    default void when_editedShape(Shape shape) {

    }
    default void when_selectedShape(Shape shape){

    }
    default void when_deselectedShape(Shape shape){

    }
}
