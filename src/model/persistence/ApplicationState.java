package model.persistence;

import model.*;
import model.Shape;
import model.dialogs.DialogProvider;
import model.interfaces.*;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ApplicationState implements IApplicationState {
    private final IUiModule uiModule;
    private final IDialogProvider dialogProvider;
    private ShapeType activeShapeType;
    private ShapeColor activePrimaryColor;
    private ShapeColor activeSecondaryColor;
    private ShapeShadingType activeShapeShadingType;
    private MouseMode activeMouseMode;

    private PaintCanvasBase paintCanvas;

    private ArrayList<Shape> shapes;
    private ArrayList<Shape> selected;




    public ApplicationState(IUiModule uiModule, PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
        this.uiModule = uiModule;
        this.dialogProvider = new DialogProvider(this);
        setDefaults();
        this.shapes = new ArrayList<>();
        this.selected = new ArrayList<>();
    }

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    public void removeShape(Shape shape){
        shapes.remove(shape);
    }

    @Override
    public void UNDO(){
        ICommand command = new UndoCommand();
        try{command.run();} catch (IOException x) {System.out.println("IOException with undo.");}
    }

    @Override
    public void REDO(){
        ICommand command = new RedoCommand();
        try{command.run();} catch (IOException x) {System.out.println("IOException with redo.");}
    }

    @Override
    public void setActiveShape() {
        activeShapeType = uiModule.getDialogResponse(dialogProvider.getChooseShapeDialog());
    }

    @Override
    public void setActivePrimaryColor() {
        activePrimaryColor = uiModule.getDialogResponse(dialogProvider.getChoosePrimaryColorDialog());
    }

    @Override
    public void setActiveSecondaryColor() {
        activeSecondaryColor = uiModule.getDialogResponse(dialogProvider.getChooseSecondaryColorDialog());
    }

    @Override
    public void setActiveShadingType() {
        activeShapeShadingType = uiModule.getDialogResponse(dialogProvider.getChooseShadingTypeDialog());
    }

    @Override
    public void setActiveStartAndEndPointMode() {
        activeMouseMode = uiModule.getDialogResponse(dialogProvider.getChooseStartAndEndPointModeDialog());
    }

    @Override
    public ShapeType getActiveShapeType() {
        return activeShapeType;
    }

    @Override
    public ShapeColor getActivePrimaryColor() {
        return activePrimaryColor;
    }

    @Override
    public ShapeColor getActiveSecondaryColor() {
        return activeSecondaryColor;
    }

    @Override
    public ShapeShadingType getActiveShapeShadingType() {
        return activeShapeShadingType;
    }

    @Override
    public MouseMode getActiveMouseMode() {
        return activeMouseMode;
    }

    public ArrayList<Shape> getSelected(){
        return selected;
    }

    public void setSelected(ArrayList<Shape> s){
        this.selected = s;
    }

    public ArrayList<Shape> getShapes(){
        return shapes;
    }

    public void setShapes(ArrayList<Shape> s){
        this.shapes = s;
    }

    public void drawShapes() {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();

        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(0, 0, paintCanvas.getWidth(), paintCanvas.getHeight());

        IDrawStrategy drawStrategy;

        boolean isSelected;
        for (Shape shape : shapes) {
            isSelected = selected.contains(shape);
            switch(shape.getShade()){
                case FILLED_IN:
                    drawStrategy = new FilledStrategy();
                    break;
                case OUTLINE:
                    drawStrategy = new OutlineStrategy();
                    break;
                case OUTLINE_AND_FILLED_IN:
                    drawStrategy = new FilledOutlineStrategy();
                    break;
                default:
                    continue;
            }
            drawStrategy.draw(shape, paintCanvas);
        }
    }

    private void setDefaults() {
        activeShapeType = ShapeType.RECTANGLE;
        activePrimaryColor = ShapeColor.BLUE;
        activeSecondaryColor = ShapeColor.GREEN;
        activeShapeShadingType = ShapeShadingType.FILLED_IN;
        activeMouseMode = MouseMode.DRAW;
    }
}
