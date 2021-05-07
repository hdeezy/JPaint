package model;

import view.interfaces.PaintCanvasBase;
import model.*;
import java.awt.*;
import java.util.EnumMap;

public class Shape {
    private Point topLeft; // bounding box borders
    private Point bottomRight;
    private ShapeType shape;
    private ShapeShadingType shade;
    private ShapeColor color;
    private ShapeColor color2;

    public Shape(Point topLeft, Point bottomRight, ShapeType shape, ShapeShadingType shade, ShapeColor color, ShapeColor color2){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.shape = shape;
        this.shade = shade;
        this.color = color;
        this.color2 = color2;
    }

    public Point getTopLeft(){return topLeft;}
    public Point getBottomRight(){return bottomRight;}
    public ShapeType getShape(){return shape;}
    public ShapeShadingType getShade(){return shade;}
    public ShapeColor getColor(){return color;}
    public ShapeColor getColor2(){return color2;}


    public void setTopLeft(Point x){this.topLeft = x;}
    public void setBottomRight(Point x){this.bottomRight = x;}
    public void setShape(ShapeType t){this.shape = t;}
    public void setShade(ShapeShadingType t){this.shade = t;}
    public void setColor(ShapeColor t){this.color = t;}
    public void setColor2(ShapeColor t){this.color2 = t;}


    public void move(int dx, int dy){
        this.topLeft.x += dx;
        this.topLeft.y += dy;
        this.bottomRight.x += dx;
        this.bottomRight.y += dy;
    }

    public boolean equals(Shape s){
        if(     s.getTopLeft().equals(this.topLeft) &&
                s.getBottomRight().equals(this.bottomRight) &&
                s.getColor().equals(this.color) &&
                s.getShade().equals(this.shade) &&
                s.getShape().equals(this.shape) &&
                s.getColor2().equals(this.color2)) return true;
        else return false;
    }

    public void draw(PaintCanvasBase paintCanvas){
        Graphics2D graphics2d = paintCanvas.getGraphics2D();

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

        graphics2d.setColor(colorMap.get(color));
        if (shape.equals(ShapeType.RECTANGLE)){
            graphics2d.fillRect(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y);
        }
        else if (shape.equals(ShapeType.ELLIPSE)){
            graphics2d.fillOval(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y);
        }
        else if (shape.equals(ShapeType.TRIANGLE)){
            int[] x = {topLeft.x, bottomRight.x, topLeft.x};
            int[] y = {topLeft.y, bottomRight.y, bottomRight.y};
            graphics2d.fillPolygon(x, y, 3);
        }
    }
}
