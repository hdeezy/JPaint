package model.Commands;

import model.interfaces.ICommand;
import model.interfaces.IShapeItem;
import model.interfaces.IUndoable;
import model.persistence.AppStateHandler;
import model.persistence.ApplicationState;
import model.ShapeGroup;

import java.util.ArrayList;

public class GroupCommand implements ICommand, IUndoable {
    ApplicationState applicationState;
    AppStateHandler stateHandler;

    ArrayList<IShapeItem> selected;
    ArrayList<IShapeItem> oldSelected;

    ArrayList<IShapeItem> shapes;
    ArrayList<IShapeItem> oldShapes;

    public GroupCommand(AppStateHandler stateHandler) {
        this.stateHandler = stateHandler;
        this.applicationState = stateHandler.getAppState();

        this.oldSelected = applicationState.getSelected();
        this.selected = new ArrayList<>();

        this.oldShapes = applicationState.getShapes();
        this.shapes = new ArrayList<>();

        ShapeGroup group = new ShapeGroup();
        for(IShapeItem shape : oldSelected){
            group.addShape(shape);
        }
        System.out.println(group.getShapes().size()+" elements grouped.");
        shapes.add(group);
        selected.add(group);
    }

    @Override
    public void run() {
        applicationState.setShapes(shapes);
        applicationState.setSelected(selected);
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void undo() {
        applicationState.setShapes(oldShapes);
        applicationState.setShapes(oldSelected);
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void redo() {
        this.run();
    }
}
