package model.interfaces;

public interface IStateSubject {
    void registerObserver(IStateObserver observer);
    void removeObserver(IStateObserver observer);
}
