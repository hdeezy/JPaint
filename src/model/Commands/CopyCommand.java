package model.Commands;

import model.Shape;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IStateObserver;
import model.persistence.ApplicationState;

import java.io.IOException;
import java.util.ArrayList;

public class CopyCommand implements ICommand {

    ApplicationState applicationState;
    AppStateHandler stateHandler;

    ArrayList<Shape> newClipboard;

    public CopyCommand(AppStateHandler stateHandler) {
        this.stateHandler = stateHandler;
        this.applicationState = stateHandler.getAppState();
    }

    @Override
    public void run() throws IOException {
        applicationState.setClipboard(applicationState.getSelected());
        stateHandler.notifyObservers(applicationState);
    }
}
