package model.interfaces;

import model.Shape;
import model.ShapeColor;
import model.ShapeType;

import java.awt.*;
import java.util.EnumMap;

public class DrawOutline {
    void DrawOutline(){}
    public void draw(Shape shape, Graphics2D graphics2d) {
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

        graphics2d.setColor(colorMap.get(shape.getColor2()));
        System.out.println(graphics2d.getColor().toString());
        if (shape.equals(ShapeType.RECTANGLE)){
            graphics2d.drawRect(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y);
        }
        else if (shape.equals(ShapeType.ELLIPSE)){
            graphics2d.drawOval(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y);
        }
        else if (shape.equals(ShapeType.TRIANGLE)) {
            int[] x = {topLeft.x, bottomRight.x, topLeft.x};
            int[] y = {topLeft.y, bottomRight.y, bottomRight.y};
            graphics2d.drawPolygon(x, y, 3);
        }
    }
}
