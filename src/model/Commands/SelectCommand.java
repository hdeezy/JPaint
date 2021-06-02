package model.Commands;

import model.Shape;
import model.interfaces.IShapeItem;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class SelectCommand implements ICommand, IUndoable {

    private ApplicationState applicationState;
    private AppStateHandler stateHandler;
    private ArrayList<IShapeItem> shapes;
    private ArrayList<IShapeItem> oldSelection;
    private ArrayList<IShapeItem> selected;

    private Point start, end;
    Point topLeft;
    Point bottomLeft;
    Point topRight;
    Point bottomRight;

    private int dx, dy;

    Stack<IUndoable> steps;

    public SelectCommand(Point start, Point end, ApplicationState appState, AppStateHandler stateHandler) {
        this.start = start;
        this.end = end;
        this.applicationState = appState;
        this.stateHandler = stateHandler;

        this.shapes = applicationState.getShapes();
        this.selected = new ArrayList<IShapeItem>();

        topLeft = new Point();
        bottomRight = new Point();

        if (start.x < end.x && start.y < end.y) { // start in bottom left end in top right
            topLeft.x = start.x;
            topLeft.y = end.y;
            bottomRight.x = end.x;
            bottomRight.y = start.y;
        } else if (start.x > end.x && start.y < end.y) { // start in bottom right end in top left
            topLeft = end;
            bottomRight = start;
        } else if (start.x < end.x && start.y > end.y) { // start in top left end in bottom right
            topLeft = start;
            bottomRight = end;
        } else if (start.x > end.x && start.y > end.y) {// start in top right end in bottom left
            topLeft.x = end.x;
            topLeft.y = start.y;
            bottomRight.x = start.x;
            bottomRight.y = end.y;
        }
    }

    @Override
    public void run() {
        oldSelection = applicationState.getSelected();

        System.out.println("Selection over: x1:"+start.x+", y1:"+start.y+", x2:"+end.x+", y2:"+end.y);

        for(IShapeItem s : shapes){

            Point shapeTopLeft = s.getTopLeft();
            Point shapeBottomRight = s.getBottomRight();

            boolean inside = false;

            for (int i = shapeTopLeft.x-1; i <= shapeBottomRight.x+1; i++){
                for (int j = shapeBottomRight.y-1; j <= shapeTopLeft.y+1; j++){
                    if( isIn(new Point(i,j)) ) inside = true;
                }
            }
            if (inside){
                selected.add(s);
                System.out.println("Shape selected: x1:"+topLeft.x+", y1:"+topLeft.y+", x2:"+bottomRight.x+", y2:"+bottomRight.y);
            }
        }

        applicationState.setSelected(selected);
        stateHandler.notifyObservers(applicationState);
        CommandHistory.add(this);
    }

    // checks if point x is within bounding box where start = topLeft and end = bottomRight
    public boolean isIn(Point p){
        if(p.x >= topLeft.x && p.x <= bottomRight.x && p.y >= bottomRight.y && p.y <= topLeft.y ) return true;
        else return false;
    }

    @Override
    public void undo() {
        applicationState.setSelected(oldSelection);
        System.out.println("undo shapes select");
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void redo() {
        this.run();
    }

}

