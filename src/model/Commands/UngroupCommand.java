package model.Commands;

import model.ShapeBuilder;
import model.interfaces.ICommand;
import model.interfaces.IShapeItem;
import model.interfaces.IUndoable;
import model.persistence.AppStateHandler;
import model.persistence.ApplicationState;
import model.ShapeGroup;

import java.util.ArrayList;

public class UngroupCommand implements ICommand, IUndoable {
    ApplicationState applicationState;

    AppStateHandler stateHandler;

    ArrayList<IShapeItem> selected;
    ArrayList<IShapeItem> oldSelected;

    ArrayList<IShapeItem> shapes;
    ArrayList<IShapeItem> oldShapes;

    public UngroupCommand(AppStateHandler stateHandler) {
        ShapeBuilder bld = new ShapeBuilder();

        this.stateHandler = stateHandler;
        this.applicationState = stateHandler.getAppState();

        this.oldSelected = applicationState.getSelected();
        this.selected = new ArrayList<>();

        this.oldShapes = applicationState.getShapes();
        this.shapes = new ArrayList<>();

        for(IShapeItem shape : oldShapes){
            if (shape.getClass().equals(ShapeGroup.class)){
                ArrayList<IShapeItem> group = ((ShapeGroup) shape).getShapes();
                for(IShapeItem groupitem : group){
                    shapes.add(bld.clone(groupitem));
                    selected.add(bld.clone(groupitem));
                }
                System.out.println(group.size()+" elements ungrouped.");
            }
            else {
                shapes.add(bld.clone(shape));
                if(oldSelected.contains(shape))
                    selected.add(bld.clone(shape));
            }
        }
    }

    @Override
    public void run() {
        applicationState.setShapes(shapes);
        applicationState.setSelected(selected);
        stateHandler.notifyObservers(applicationState);
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        applicationState.setShapes(oldShapes);
        applicationState.setSelected(oldSelected);
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void redo() {
        this.run();
    }
}
