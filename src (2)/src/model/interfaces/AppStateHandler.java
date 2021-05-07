package model.interfaces;

import model.persistence.ApplicationState;

import java.util.ArrayList;
import java.util.List;

public class AppStateHandler implements IStateSubject {
    ArrayList<IStateObserver> observers = new ArrayList<>();

    ApplicationState appState;

    public AppStateHandler(ApplicationState applicationState) {
        this.appState = applicationState;
    }

    @Override
    public void registerObserver(IStateObserver observer){observers.add(observer);}

    @Override
    public void removeObserver(IStateObserver observer){observers.remove(observer);}

    public void notifyObservers(ApplicationState appState){
        for(IStateObserver observer : observers){
            observer.update(appState);
        }
    }
}
