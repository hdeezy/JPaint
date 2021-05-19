package model.Commands;

import model.Shape;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;

import java.io.IOException;
import java.util.ArrayList;

public class PasteCommand implements ICommand, IUndoable {

    ApplicationState applicationState;
    AppStateHandler stateHandler;

    ArrayList<Shape> clipboard;
    ArrayList<Shape> shapes;
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

        for (Shape shape : clipboard){
            // create new shape based on shape from clipboard
            Shape.ShapeBuilder builder = new Shape.ShapeBuilder();
            shapes.add(builder.clone(shape.move(dx,dy)));
            System.out.println("Shape pasted.");
        }
        applicationState.setShapes(shapes);
        stateHandler.notifyObservers(applicationState);
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        applicationState.setShapes((ArrayList<Shape>)oldShapes);
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
