package model.interfaces.DrawStrategies;

import model.Shape;
import model.Enums.ShapeColor;
import model.Enums.ShapeType;
import model.interfaces.IDrawStrategy;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.util.EnumMap;

import static java.lang.Math.abs;

public class FilledOutlineStrategy implements IDrawStrategy {
    @Override
    public void draw(Shape shape,  PaintCanvasBase paintCanvas) {
        Point topLeft = shape.getTopLeft();
        Point bottomRight  = shape.getBottomRight();

        EnumMap<ShapeColor, Color> colorMap = new EnumMap<ShapeColor, Color>(ShapeColor.class);
        colorMap = new EnumMap<ShapeColor, Color>(ShapeColor.class);
        colorMap.put(ShapeColor.BLACK, Color.BLACK);
        colorMap.put(ShapeColor.BLUE, Color.BLUE);
        colorMap.put(ShapeColor.CYAN, Color.CYAN);
        colorMap.put(ShapeColor.DARK_GRAY, Color.DARK_GRAY);
        colorMap.put(ShapeColor.GRAY, Color.GRAY);
        colorMap.put(ShapeColor.GREEN, Color.GREEN);
        colorMap.put(ShapeColor.LIGHT_GRAY, Color.LIGHT_GRAY);
        colorMap.put(ShapeColor.MAGENTA, Color.MAGENTA);
        colorMap.put(ShapeColor.ORANGE, Color.ORANGE);
        colorMap.put(ShapeColor.PINK, Color.PINK);
        colorMap.put(ShapeColor.RED, Color.RED);
        colorMap.put(ShapeColor.WHITE, Color.WHITE);
        colorMap.put(ShapeColor.YELLOW, Color.yellow);

        ShapeType shapeType = shape.getShape();
        if (shapeType.equals(ShapeType.RECTANGLE)){
            Graphics2D graphics2d = paintCanvas.getGraphics2D();
            graphics2d.setColor(colorMap.get(shape.getColor()));
            graphics2d.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            graphics2d.fillRect(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y);
            graphics2d.setColor(colorMap.get(shape.getColor2()));
            graphics2d.fillRect(topLeft.x+5, topLeft.y-5, bottomRight.x-topLeft.x-10, bottomRight.y-topLeft.y+10);
        } else if (shapeType.equals(ShapeType.ELLIPSE)){
            Graphics2D graphics2d = paintCanvas.getGraphics2D();
            graphics2d.setColor(colorMap.get(shape.getColor2()));
            graphics2d.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            graphics2d.fillOval(topLeft.x, topLeft.y-abs(bottomRight.y-topLeft.y), abs(bottomRight.x-topLeft.x), abs(bottomRight.y-topLeft.y));
            graphics2d.setColor(colorMap.get(shape.getColor()));
            graphics2d.drawOval(topLeft.x, topLeft.y-abs(bottomRight.y-topLeft.y), abs(bottomRight.x-topLeft.x), abs(bottomRight.y-topLeft.y));
        } else if (shapeType.equals(ShapeType.TRIANGLE)) {
            Graphics2D graphics2d = paintCanvas.getGraphics2D();
            int[] x = {topLeft.x, bottomRight.x, topLeft.x};
            int[] y = {topLeft.y, bottomRight.y, bottomRight.y};
            graphics2d.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            graphics2d.setColor(colorMap.get(shape.getColor2()));
            graphics2d.fillPolygon(x, y, 3);
            graphics2d.setColor(colorMap.get(shape.getColor()));
            graphics2d.drawPolygon(x, y, 3);
        }
    }
}
