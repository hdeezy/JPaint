package controller.DrawStrategies;

import model.Shape;
import model.interfaces.IDrawStrategy;
import view.interfaces.PaintCanvasBase;

public class DrawStrategy {
    private IDrawStrategy strategy;

    public void setStrategy(IDrawStrategy strategy){
        this.strategy = strategy;
    }

    public void draw(Shape shape, PaintCanvasBase paintCanvas) {
        strategy.draw(shape, paintCanvas);
    }
}
