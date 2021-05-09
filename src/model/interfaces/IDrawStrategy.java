package model.interfaces;

import model.Shape;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public interface IDrawStrategy {
    void draw(Shape shape, PaintCanvasBase paintCanvas);
}
