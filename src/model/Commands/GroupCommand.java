package model.Commands;

import model.Shape;
import model.interfaces.ICommand;
import model.interfaces.IShapeItem;
import model.interfaces.IUndoable;
import model.persistence.AppStateHandler;
import model.persistence.ApplicationState;
import model.persistence.ShapeGroup;

import java.io.IOException;
import java.util.ArrayList;

public class GroupCommand implements ICommand, IUndoable {
    ApplicationState applicationState;
    AppStateHandler stateHandler;

    ArrayList<IShapeItem> selected;
    ArrayList<IShapeItem> oldShapes;

    ArrayList<IShapeItem> shapes;

    public GroupCommand(AppStateHandler stateHandler) {
        this.stateHandler = stateHandler;
        this.applicationState = stateHandler.getAppState();
        this.shapes = applicationState.getShapes();
        this.oldShapes = shapes;
        ShapeGroup group = new ShapeGroup();
        for(IShapeItem shape : selected){
            group.addShape(shape);
        }
        shapes.add(group);
    }

    @Override
    public void run() {
        applicationState.setShapes(shapes);
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void undo() {
        applicationState.setShapes(oldShapes);
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void redo() {
        this.run();
    }
}
