package controller.mouse;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;

import model.MouseMode;
import model.ShapeType;
import model.interfaces.*;
import model.Commands.MoveCommand;
import model.Commands.SelectClickCommand;
import model.Commands.SelectCommand;
import model.Commands.ShapeCommand;
import model.persistence.AppStateHandler;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.io.IOException;

public class IMouseListener implements MouseListener, IStateObserver {

    PaintCanvasBase paintCanvas;
    ApplicationState appState;
    AppStateHandler stateHandler;

    public IMouseListener(ApplicationState appState, AppStateHandler stateHandler){
        this.paintCanvas = paintCanvas;
        this.appState = appState;
        this.stateHandler = stateHandler;
    }

    private Point start, end = new Point();

    public Point getStart(){
        return start;
    }

    public Point getEnd(){
        return end;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Point click = e.getPoint();
        ICommand cmd;
        if (appState.getActiveMouseMode().equals(MouseMode.SELECT)){
            cmd = new SelectClickCommand(click, appState, stateHandler);
            try{cmd.run();} catch (IOException x) { }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        end = e.getPoint();
        /*
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.GREEN);
        graphics2d.fillRect(start.x, start.y, end.x-start.x, end.y-start.y);
         */
        ICommand cmd;
        if (appState.getActiveMouseMode().equals(MouseMode.DRAW)){
            if (appState.getActiveShapeType().equals(ShapeType.RECTANGLE)){
                cmd = new ShapeCommand(start, end, appState, stateHandler);
                try{cmd.run();} catch (IOException x) { }
            }
            else if (appState.getActiveShapeType().equals(ShapeType.ELLIPSE)){
                cmd = new ShapeCommand(start, end, appState, stateHandler);
                try{cmd.run();} catch (IOException x) { }
            }
            else if (appState.getActiveShapeType().equals(ShapeType.TRIANGLE)){
                cmd = new ShapeCommand(start, end, appState, stateHandler);
                try{cmd.run();} catch (IOException x) { }
            }
        }
        else if (appState.getActiveMouseMode().equals(MouseMode.SELECT)){
            cmd = new SelectCommand(start, end, appState, stateHandler);
            try{cmd.run();} catch (IOException x) { }
        }
        else if (appState.getActiveMouseMode().equals(MouseMode.MOVE)){
            cmd = new MoveCommand(start, end, appState, stateHandler);
            try{cmd.run();} catch (IOException x) { }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void update(ApplicationState AppState) {
        this.appState = appState;
    }

}
