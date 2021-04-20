package commands;

import model.persistence.ApplicationState;
import mouse.*;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;
import java.util.Stack;

public class DrawCommand implements ICommand, IUndoable {

    PaintCanvasBase paintCanvas;
    IMouseListener  mouse;

    private Point start, end;

    Stack<IUndoable> undoStack;

    public DrawCommand(PaintCanvasBase paintCanvas, IMouseListener mouse) {
        this.paintCanvas = paintCanvas;
        this.mouse = mouse;
    }

    @Override
    public void run() {
        start = mouse.getStart();
        end = mouse.getEnd();

        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.GREEN);
        graphics2d.fillRect(start.x, start.y, end.x-start.x, end.y-start.y);

        undoStack =  new Stack<IUndoable>();
        undoStack = CommandHistory.undoStack;

        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(start.x, start.y, end.x-start.x, end.y-start.y);
    }

    @Override
    public void redo() {
        this.run();
    }

}
