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

public class SelectClickCommand implements ICommand, IUndoable {

    private PaintCanvasBase paintCanvas;
    private ApplicationState applicationState;
    private AppStateHandler stateHandler;
    private ArrayList<IShapeItem> shapes;
    private ArrayList<IShapeItem> oldSelection;
    private ArrayList<IShapeItem> selected;

    private Point click;


    private int dx, dy;

    Stack<IUndoable> steps;

    public SelectClickCommand(Point click, ApplicationState appState, AppStateHandler stateHandler) {
        this.paintCanvas = paintCanvas;
        this.click = click;
        this.applicationState = appState;
        this.stateHandler = stateHandler;
        this.shapes = applicationState.getShapes();
        this.selected = new ArrayList<IShapeItem>();

    }
    public Rectangle toRectangle(Point topLeft, Point bottomRight){
        return new Rectangle(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, topLeft.y - bottomRight.y);
    }
    @Override
    public void run() {
        oldSelection = applicationState.getSelected();

        System.out.println("Selection at: x:"+click.x+", y1:"+click.y);

        for(IShapeItem s : shapes){
            Point shapeTopLeft = s.getTopLeft();
            Point shapeBottomRight = s.getBottomRight();

            boolean inside = false;

            // double loop works while comparing boundaries to the point does not bc offset is different for
            // different shape drawing methods
            for (int i = shapeTopLeft.x-1; i <= shapeBottomRight.x+1; i++){
                for (int j = shapeBottomRight.y-1; j <= shapeTopLeft.y+1; j++){
                    if( click.x == i && click.y == j ) inside = true;
                }
            }



            if (inside){
                selected.add(s);
                System.out.println("Shape selected at x:"+click.x+", y:"+click.y);
            }
        }

        applicationState.setSelected(selected);
        applicationState.drawShapes();
        stateHandler.notifyObservers(applicationState);
        CommandHistory.add(this);
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
    }

}

