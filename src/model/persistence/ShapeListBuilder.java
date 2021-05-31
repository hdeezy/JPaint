package model.persistence;

import model.Shape;
import model.interfaces.IShapeItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShapeListBuilder {
    List<IShapeItem> shapeList = new ArrayList<>();

    Map<String, String> parentMappings = new HashMap<>();

    public void addShape(Shape shape, String name, String parent){
        Shape.ShapeBuilder bld = new Shape.ShapeBuilder();
        shapeList.add( bld.clone(shape) );
        parentMappings.put(name, parent);
    }

    public IShapeItem toShapeList(){
        IShapeItem root = null;

        for(IShapeItem shapeItem : shapeLIst){
            String parent = parentMappings.get(shapeItem.getName());

            if(parent == null)
                root = shapeItem;
            else {
                ShapeGroup parentGroup = getGroup(parent);
                parentGroup.addChild(shapeItem);
            }
        }
        return root;
    }

    private ShapeGroup getGroup(String parent){
        for(IShapeItem shapeItem : shapeList){
            if(shapeItem.getName().equals(parent))
                return (ShapeGroup)shapeItem;
        }
        throw new Error("No parent found!");
    }
}
