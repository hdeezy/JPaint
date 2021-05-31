package model.persistence;

import model.interfaces.IShapeItem;

public class ShapeListFactory {
    public static IShapeItem getEmptyShapes(){
        ShapeListBuilder builder = new ShapeListBuilder();
    }
}
