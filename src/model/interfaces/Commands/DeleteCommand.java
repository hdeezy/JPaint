package model.interfaces.Commands;

import model.Shape;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IStateObserver;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;

import java.util.ArrayList;

public class DeleteCommand implements ICommand, IUndoable, IStateObserver {
    ApplicationState applicationState;
    AppStateHandler stateHandler;

    ArrayList<Shape> selected;
    ArrayList<Shape> oldShapes;
    ArrayList<Shape> shapes;

    public DeleteCommand(ApplicationState appState, AppStateHandler stateHandler) {
        this.applicationState = appState;
        this.stateHandler = stateHandler;
        this.oldShapes = applicationState.getShapes();
        this.selected = applicationState.getSelected();

    }

    @Override
    public void run(){
        shapes = oldShapes;
        for(Shape shape : selected){
            shapes.remove(shape);
            System.out.println("Deleted shape.");
        }
        applicationState.setShapes(shapes);
        stateHandler.notifyObservers(applicationState);
        applicationState.drawShapes();
        CommandHistory.add(this);
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

    @Override
    public void update(ApplicationState AppState) {
        applicationState = AppState;
    }
}
