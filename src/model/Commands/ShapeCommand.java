package model.Commands;

import model.ShapeBuilder;
import model.persistence.AppStateHandler;
import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;
import model.Shape;

import java.awt.*;

public class ShapeCommand implements ICommand, IUndoable {

    private PaintCanvasBase paintCanvas;
    private ApplicationState applicationState;
    private AppStateHandler stateHandler;
    private Shape shape;

    private Point start, end;


    public ShapeCommand(Point start, Point end, ApplicationState appState, AppStateHandler stateHandler) {
        this.paintCanvas = paintCanvas;
        this.start = start;
        this.end = end;
        this.applicationState = appState;
        this.stateHandler = stateHandler;

        Point topLeft = new Point();
        Point bottomRight = new Point();

        // account for direction of drag in creating shape
        if (start.x <= end.x && start.y <= end.y) { // start in bottom left end in top right
            topLeft.x = start.x;
            topLeft.y = end.y;
            bottomRight.x = end.x;
            bottomRight.y = start.y;
        } else if (start.x >= end.x && start.y <= end.y) { // start in bottom right end in top left
            topLeft = end;
            bottomRight = start;
        } else if (start.x <= end.x && start.y >= end.y) { // start in top left end in bottom right
            topLeft = start;
            bottomRight = end;
        } else if (start.x >= end.x && start.y >= end.y) {// start in top right end in bottom left
            topLeft.x = end.x;
            topLeft.y = start.y;
            bottomRight.x = start.x;
            bottomRight.y = end.y;
        }
        //this.shape = new Shape(topLeft, bottomRight, applicationState.getActiveShapeType(), applicationState.getActiveShapeShadingType(),
        //        applicationState.getActivePrimaryColor(), applicationState.getActiveSecondaryColor());
        ShapeBuilder builder = new ShapeBuilder();
        builder.setTopLeft(topLeft);
        builder.setBottomRight(bottomRight);
        builder.setShape(applicationState.getActiveShapeType());
        builder.setShade(applicationState.getActiveShapeShadingType());
        builder.setColor(applicationState.getActivePrimaryColor());
        builder.setColor2(applicationState.getActiveSecondaryColor());
        this.shape = builder.build();

    }

    @Override
    public void run() {
        applicationState.addShape(shape);
        CommandHistory.add(this);
        applicationState.drawShapes();
        stateHandler.notifyObservers(applicationState);
    }

    @Override
    public void undo() {
        applicationState.removeShape(shape);
        stateHandler.notifyObservers(applicationState);
        applicationState.drawShapes();
    }

    @Override
    public void redo() {
        this.run();
    }

}
