package model.interfaces;

import model.Shape;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class DrawStrategy {
    private IDrawStrategy strategy;

    public void setStrategy(IDrawStrategy strategy){
        this.strategy = strategy;
    }

    public void draw(Shape shape, PaintCanvasBase paintCanvas) {
        strategy.draw(shape, paintCanvas);
    }
}
