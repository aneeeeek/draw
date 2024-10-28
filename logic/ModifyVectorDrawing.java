package logic;

import shapes.Shape;

public interface ModifyVectorDrawing {
    public void lower(Shape s);

    public void raise(Shape s);

    public void removeShape(Shape s);

    public void insertShape(Shape s);
}