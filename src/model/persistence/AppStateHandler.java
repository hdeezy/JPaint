package model.persistence;

import model.interfaces.IStateObserver;
import model.interfaces.IStateSubject;
import model.persistence.ApplicationState;

import java.util.ArrayList;

public class AppStateHandler implements IStateSubject {
    ArrayList<model.interfaces.IStateObserver> observers = new ArrayList<>();

    ApplicationState appState;

    public AppStateHandler(ApplicationState applicationState) {
        this.appState = applicationState;
    }

    @Override
    public void registerObserver(model.interfaces.IStateObserver observer){observers.add(observer);}

    @Override
    public void removeObserver(model.interfaces.IStateObserver observer){observers.remove(observer);}

    public void notifyObservers(ApplicationState appState){
        for(IStateObserver observer : observers){
            observer.update(appState);
        }
        appState.drawShapes();
    }

    public ApplicationState getAppState(){
        return appState;
    }
}
