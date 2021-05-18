package model.interfaces.Commands;

import model.Shape;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IStateObserver;
import model.persistence.ApplicationState;

import java.io.IOException;
import java.util.ArrayList;

public class CopyCommand implements ICommand, IStateObserver {

    ApplicationState applicationState;
    AppStateHandler stateHandler;

    ArrayList<Shape> oldClipboard;
    ArrayList<Shape> newClipboard;

    public CopyCommand(ApplicationState appState, AppStateHandler stateHandler) {
        this.applicationState = appState;
        this.stateHandler = stateHandler;
        this.oldClipboard = new ArrayList<>();
        this.newClipboard = new ArrayList<>();
    }

    @Override
    public void run() throws IOException {
        oldClipboard = applicationState.getClipboard();
        newClipboard = applicationState.getSelected();
        applicationState.setClipboard(newClipboard);
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void update(ApplicationState AppState) {
        applicationState = AppState;
    }
}
