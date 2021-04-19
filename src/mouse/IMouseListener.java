package mouse;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;
import controller.IJPaintController;
import controller.JPaintController;
import model.ShapeColor;
import model.ShapeType;
import model.persistence.ApplicationState;
import view.gui.Gui;
import view.gui.GuiWindow;
import view.gui.PaintCanvas;
import view.interfaces.IGuiWindow;
import view.interfaces.PaintCanvasBase;
import view.interfaces.IUiModule;
import mouse.*;

import java.awt.*;
import java.util.Collection;
import java.util.EnumMap;
public class IMouseListener implements MouseListener {

    PaintCanvasBase paintCanvas;

    public IMouseListener(PaintCanvasBase paintCanvas){
        this.paintCanvas = paintCanvas;
    }

    private Point start, end = new Point();

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        end = e.getPoint();
        Graphics2D graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(Color.GREEN);
        graphics2d.fillRect(start.x, start.y, end.x-start.x, end.y-start.y);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }

}
