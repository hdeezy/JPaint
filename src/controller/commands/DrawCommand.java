package controller.commands;

import controller.mouse.*;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.util.Stack;

public class DrawCommand implements ICommand, IUndoable {

    PaintCanvasBase paintCanvas;
    IMouseListener  mouse;

    private Point start, end;

    Stack<IUndoable> steps;

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

        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();

        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(0, 0, paintCanvas.getWidth(), paintCanvas.getHeight());

        graphics2d.setColor(Color.GREEN);

        steps = CommandHistory.getUndoStack();

        while( !steps.isEmpty()){
            steps = CommandHistory.getUndoStack();
            DrawCommand cmd = ((DrawCommand)steps.pop());
            graphics2d.fillRect(cmd.start.x, cmd.start.y, cmd.end.x-cmd.start.x, cmd.end.y-cmd.start.y);
        }
    }

    @Override
    public void redo() {
        //this.run();
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.GREEN);
        Point start = this.mouse.getStart();
        Point end = this.mouse.getEnd();
        graphics2d.fillRect(start.x, start.y, end.x-start.x, end.y-start.y);
    }

}
