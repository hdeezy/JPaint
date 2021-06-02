package model.Commands;

import model.interfaces.ICommand;

public class UndoCommand implements ICommand {
    @Override
    public void run(){
        CommandHistory.undo();
    }
}
