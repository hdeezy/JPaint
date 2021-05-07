package model.interfaces;

import model.persistence.ApplicationState;

public interface IStateObserver {
    void update(ApplicationState AppState);
}
