package model.persistence;

import model.Shape;
import model.interfaces.IShapeItem;

import java.awt.*;
import java.util.ArrayList;

public class ShapeGroup implements IShapeItem {
    private ArrayList<IShapeItem> shapes;

    private String name;

    public void Group(){
        shapes = new ArrayList<>();
    }

    public void addShape(IShapeItem shape){
        shapes.add(shape);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void move(int dx, int dy){
        for (IShapeItem shape : shapes){
            shape.move(dx, dy);
        }
    }

    @Override
    public Point getTopLeft(){


        return topLeft;
    }

    @Override
    public Point getBottomRight(){


        return bottomRight;
    }
}
