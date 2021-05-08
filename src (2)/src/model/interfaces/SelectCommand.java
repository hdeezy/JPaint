package model.interfaces;

import model.Shape;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class SelectCommand implements ICommand, IUndoable {

    private PaintCanvasBase paintCanvas;
    private ApplicationState applicationState;
    private AppStateHandler stateHandler;
    private ArrayList<Shape> shapes;
    private ArrayList<Shape> oldSelection;
    private ArrayList<Shape> selected;

    private Point start, end;

    private int dx, dy;

    Stack<IUndoable> steps;

    public SelectCommand(Point start, Point end, ApplicationState appState, AppStateHandler stateHandler) {
        this.paintCanvas = paintCanvas;
        this.start = start;
        this.end = end;
        this.applicationState = appState;
        this.stateHandler = stateHandler;
        this.shapes = applicationState.getShapes();
        this.selected = new ArrayList<Shape>();
    }

    @Override
    public void run() {
        oldSelection = applicationState.getSelected();

        System.out.println("Selection over: x1:"+start.x+", y1:"+start.y+", x2:"+end.x+", y2:"+end.y);

        Point topLeft = new Point();
        Point bottomLeft = new Point();
        Point topRight = new Point();
        Point bottomRight = new Point();

        for(Shape s : shapes){
            topLeft = s.getTopLeft();
            bottomRight = s.getBottomRight();
            bottomLeft.x = topLeft.x;
            bottomLeft.y = bottomRight.y;
            topRight.x = bottomRight.x;
            topRight.y = topLeft.y;

            if ( isIn(topLeft) || isIn(topRight) || isIn(bottomLeft) || isIn(bottomRight)){
                selected.add(s);
                System.out.println("Shape selected: x1:"+topLeft.x+", y1:"+topLeft.y+", x2:"+bottomRight.x+", y2:"+bottomRight.y);
            }
        }
        applicationState.setSelected(selected);
        applicationState.drawShapes();
        stateHandler.notifyObservers(applicationState);
        CommandHistory.add(this);
    }

    // checks if point x is within bounding box where start = topLeft and end = bottomRight
    public boolean isIn(Point p){
        if(p.x >= start.x && p.x <= end.x && p.y >= end.y && p.y <= start.y ) return true;
        else if(p.x <= start.x && p.x <= end.x && p.y <= end.y && p.y >= start.y ) return true;

        else return false;
    }

    @Override
    public void undo() {
        applicationState.setSelected(oldSelection);
        System.out.println("undo shapes select");
        stateHandler.notifyObservers(applicationState);
        applicationState.drawShapes();
    }

    @Override
    public void redo() {
        this.run();
        //Graphics2D graphics2d = paintCanvas.getGraphics2D();
        //graphics2d.setColor(Color.GREEN);
        //graphics2d.fillRect(start.x, start.y, end.x-start.x, end.y-start.y);
    }

}

