package model;

import model.interfaces.IShapeItem;

import java.awt.*;

public class Shape implements IShapeItem{
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

    private Shape(Shape s){
        this.topLeft = s.getTopLeft();
        this.bottomRight = s.getBottomRight();
        this.shape = s.getShape();
        this.shade = s.getShade();
        this.color = s.getColor();
        this.color2 = s.getColor2();
    }

    public int getHeight(){return bottomRight.y - topLeft.y;}
    public int getWidth(){return bottomRight.x - topLeft.x;}

    @Override
    public Point getTopLeft(){return topLeft;}
    @Override
    public Point getBottomRight(){return bottomRight;}

    public ShapeType getShape(){return shape;}
    public ShapeShadingType getShade(){return shade;}
    public ShapeColor getColor(){return color;}
    public ShapeColor getColor2(){return color2;}


    public void move(int dx, int dy){
        this.topLeft.x += dx;
        this.topLeft.y += dy;
        this.bottomRight.x += dx;
        this.bottomRight.y += dy;
    }

    public boolean equals(Shape s){
        return s.getTopLeft().equals(this.topLeft) &&
                s.getBottomRight().equals(this.bottomRight) &&
                s.getColor().equals(this.color) &&
                s.getShade().equals(this.shade) &&
                s.getShape().equals(this.shape) &&
                s.getColor2().equals(this.color2);
    }



}
