package model.interfaces;

import model.Shape;

import java.awt.*;

public interface IShapeItem {
    String getName();
    void setName(String name);
    public void move(int dx, int dy);
    Point getTopLeft();
    Point getBottomRight();
}
