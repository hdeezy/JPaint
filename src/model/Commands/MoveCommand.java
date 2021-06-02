package model.Commands;

import model.Shape;
import model.ShapeBuilder;
import model.interfaces.IShapeItem;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;

import java.awt.*;
import java.util.ArrayList;

public class MoveCommand implements ICommand, IUndoable {

    private ApplicationState applicationState;
    private AppStateHandler stateHandler;
    private ArrayList<IShapeItem> selected;
    private ArrayList<IShapeItem> shapes;
    Object oldShapes;


    private int dx, dy;

    public MoveCommand(Point start, Point end, ApplicationState appState, AppStateHandler stateHandler) {
        this.applicationState = appState;
        this.stateHandler = stateHandler;
        this.selected = appState.getSelected();
        this.shapes = appState.getShapes();
        this.dx = end.x - start.x;
        this.dy = end.y - start.y;
    }

    @Override
    public void run() {
        oldShapes = shapes.clone();
        ShapeBuilder builder = new ShapeBuilder();
        ArrayList<IShapeItem> newSel = new ArrayList<>();

        for(IShapeItem sel : selected){
            if(shapes.contains(sel)){
                shapes.remove(sel);
                sel.move(dx,dy);
                IShapeItem newShape = builder.clone(sel);
                shapes.add(newShape);
                newSel.add(newShape);
                System.out.println("moved shape");
            }
        }
        applicationState.setSelected(newSel);
        applicationState.setShapes(shapes);
        stateHandler.notifyObservers(applicationState);
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        applicationState.setShapes((ArrayList<IShapeItem>)oldShapes);
        applicationState.setSelected(selected);

        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void redo() {
        this.run();
    }

}






