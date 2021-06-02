package model.Commands;

import model.Shape;
import model.interfaces.IShapeItem;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IStateObserver;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;

import java.util.ArrayList;

public class DeleteCommand implements ICommand, IUndoable {
    ApplicationState applicationState;
    AppStateHandler stateHandler;

    ArrayList<IShapeItem> selected;
    ArrayList<IShapeItem> shapes;

    public DeleteCommand(AppStateHandler stateHandler) {
        this.stateHandler = stateHandler;
        this.applicationState = stateHandler.getAppState();
        this.shapes = applicationState.getShapes();
        this.selected = applicationState.getSelected();
    }

    @Override
    public void run(){

        for(IShapeItem shape : selected){
            shapes.remove(shape);
            System.out.println("Deleted shape.");
        }
        applicationState.setShapes(shapes);
        applicationState.setSelected(new ArrayList<IShapeItem>());
        stateHandler.notifyObservers(applicationState);
        CommandHistory.add(this);
    }


    @Override
    public void undo() {
        for (IShapeItem shape : selected){
            shapes.add(shape);
            System.out.println("Undo delete shape.");
        }
        applicationState.setSelected(selected);
        applicationState.setShapes(shapes);
        applicationState.drawShapes();
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void redo() {
        this.run();
    }
}
