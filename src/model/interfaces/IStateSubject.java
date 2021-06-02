package model.interfaces;

public interface IStateSubject {
    void registerObserver(model.interfaces.IStateObserver observer);
    void removeObserver(IStateObserver observer);
}
