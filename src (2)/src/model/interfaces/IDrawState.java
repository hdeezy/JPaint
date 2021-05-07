package model.interfaces;

import model.Shape;
import view.interfaces.PaintCanvasBase;

public interface IDrawState {
    void drawShape(Shape shape, PaintCanvasBase canvas);
}
