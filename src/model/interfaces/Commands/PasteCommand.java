package model.interfaces.Commands;

import model.Shape;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IStateObserver;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;

import java.io.IOException;
import java.util.ArrayList;

public class PasteCommand implements ICommand, IUndoable, IStateObserver {

    ApplicationState applicationState;
    AppStateHandler stateHandler;

    ArrayList<Shape> clipboard;
    ArrayList<Shape> oldShapes;
    ArrayList<Shape> newShapes;


    public PasteCommand(ApplicationState appState, AppStateHandler stateHandler) {
        this.applicationState = appState;
        this.stateHandler = stateHandler;
        this.clipboard = applicationState.getClipboard();
        this.oldShapes = applicationState.getShapes();
        this.newShapes = applicationState.getShapes();
    }

    @Override
    public void run() throws IOException {
        for (Shape shape : clipboard){
            newShapes.add(shape);
        }

        applicationState.setShapes(newShapes);
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
        try {
            this.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ApplicationState AppState) {
        applicationState = AppState;
    }
}
