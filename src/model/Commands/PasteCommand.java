package model.Commands;

import model.ShapeBuilder;
import model.interfaces.IShapeItem;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;

import java.io.IOException;
import java.util.ArrayList;

public class PasteCommand implements ICommand, IUndoable {

    ApplicationState applicationState;
    AppStateHandler stateHandler;

    ArrayList<IShapeItem> clipboard;
    ArrayList<IShapeItem> shapes;
    Object oldShapes;


    int dx = 20;
    int dy = 20;

    public PasteCommand(AppStateHandler stateHandler) {
        this.stateHandler = stateHandler;
        this.applicationState = stateHandler.getAppState();
    }

    @Override
    public void run() throws IOException {
        this.clipboard = applicationState.getClipboard();
        this.shapes = applicationState.getShapes();
        oldShapes = shapes.clone();

        for (IShapeItem shape : clipboard){
            // create new shape based on shape from clipboard
            ShapeBuilder builder = new ShapeBuilder();
            shape.move(dx,dy);
            Object newShape = builder.clone(shape);
            shapes.add((IShapeItem) newShape);
            shape.move(-dx,-dy);
            System.out.println("Paste done.");
        }
        applicationState.setShapes(shapes);
        stateHandler.notifyObservers(applicationState);
        CommandHistory.add(this);
    }


    @Override
    public void undo() {
        applicationState.setShapes((ArrayList<IShapeItem>)oldShapes);
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
}
