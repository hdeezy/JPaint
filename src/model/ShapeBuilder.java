package model;

import model.interfaces.IShapeItem;

import java.awt.Point;

public class ShapeBuilder {
    private Point topLeft; // bounding box borders
    private Point bottomRight;
    private ShapeType shape;
    private ShapeShadingType shade;
    private ShapeColor color;
    private ShapeColor color2;

    public void setTopLeft(Point x){this.topLeft = (Point) x.clone();}
    public void setBottomRight(Point x){this.bottomRight = (Point) x.clone();}
    public void setShape(ShapeType t){this.shape = t;}
    public void setShade(ShapeShadingType t){this.shade = t;}
    public void setColor(ShapeColor t){this.color = t;}
    public void setColor2(ShapeColor t){this.color2 = t;}

    public IShapeItem clone(IShapeItem shape){
        if (shape.getClass().equals(Shape.class)){
            setTopLeft(shape.getTopLeft());
            setBottomRight(shape.getBottomRight());
            setColor(((Shape) shape).getColor());
            setColor2(((Shape) shape).getColor2());
            setShade(((Shape) shape).getShade());
            setShape(((Shape) shape).getShape());
            return (build());
        }
        else { // else if ShapeGroup
            ShapeGroup oldGroup = (ShapeGroup) shape;
            ShapeGroup newGroup = new ShapeGroup();

            for (IShapeItem s : oldGroup.getShapes()){
                    ShapeBuilder bld = new ShapeBuilder();
                    newGroup.addShape(bld.clone(s));
            }
            return newGroup;

        }
    }

    public Shape build(){return new Shape(topLeft,bottomRight,shape,shade,color,color2);}

}
