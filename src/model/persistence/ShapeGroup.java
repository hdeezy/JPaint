package model.persistence;

import model.Shape;
import model.interfaces.IShapeItem;

import java.awt.*;
import java.util.ArrayList;

public class ShapeGroup implements IShapeItem {
    private ArrayList<IShapeItem> shapes =  new ArrayList<>();

    private Point topLeft;
    private Point bottomRight;

    public ShapeGroup(){
        topLeft = new Point();
        topLeft.x = 0;
        topLeft.y = 0;
        bottomRight = new Point();
        bottomRight.x = 0;
        bottomRight.y = 0;
    }

    public void addShape(IShapeItem shape){
        shapes.add(shape);
    }

    public void addShapes(ArrayList<IShapeItem> shapelist){
        for (IShapeItem s : shapelist){
            shapes.add(s);
        }
    }

    public void removeShape(IShapeItem shape){
        shapes.remove(shape);
    }

    public void removeShapes(ArrayList<IShapeItem> shapelist){
        for (IShapeItem s : shapelist){
            shapes.remove(s);
        }
    }

    public ArrayList<IShapeItem> getShapes(){ return shapes; }


    @Override
    public void move(int dx, int dy){
        for (IShapeItem shape : shapes){
            shape.move(dx, dy);
        }
    }

    @Override
    public Point getTopLeft(){
        Point p;
        for (IShapeItem shape : shapes){
            // get top-leftmost boundaries among group
            p = shape.getTopLeft();
            if (p.x < this.topLeft.x || this.topLeft.x == 0) this.topLeft.x = p.x;
            if (p.y > this.topLeft.y || this.topLeft.y == 0) this.topLeft.y = p.y;
        }
        return topLeft;
    }

    @Override
    public Point getBottomRight(){
        Point p;
        for (IShapeItem shape : shapes) {
            // get bottom-rightmost boundaries among group
            p = shape.getBottomRight();
            if (p.x > this.bottomRight.x || this.bottomRight.x == 0) this.bottomRight.x = p.x;
            if (p.y < this.bottomRight.y || this.bottomRight.y == 0) this.bottomRight.y = p.y;
        }
        return bottomRight;
    }
}
