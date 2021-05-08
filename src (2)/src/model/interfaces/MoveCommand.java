package model.interfaces;

import model.Shape;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class MoveCommand implements ICommand, IUndoable {

    private PaintCanvasBase paintCanvas;
    private ApplicationState applicationState;
    private AppStateHandler stateHandler;
    private ArrayList<Shape> selected;
    private ArrayList<Shape> shapes;
    private Point start, end;

    private int dx, dy;

    Stack<IUndoable> steps;

    public MoveCommand(Point start, Point end, ApplicationState appState, AppStateHandler stateHandler) {
        this.paintCanvas = paintCanvas;
        this.start = start;
        this.end = end;
        this.applicationState = appState;
        this.stateHandler = stateHandler;
        this.selected = appState.getSelected();
        this.shapes = appState.getShapes();
        this.dx = end.x - start.x;
        this.dy = end.y - start.y;
    }

    @Override
    public void run() {
        for(Shape s : shapes){
            if(selected.contains(s)){
                s.move(dx, dy);
                System.out.println("moved shape");
            }
        }
        CommandHistory.add(this);
        applicationState.setShapes(shapes);
        applicationState.drawShapes();
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void undo() {
        for(Shape s : shapes){
            if(selected.contains(s)) {
                s.move(-dx, -dy);
                System.out.println("undo shape move");
            }
        }
        applicationState.setShapes(shapes);
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






