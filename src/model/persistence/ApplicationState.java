package model.persistence;

import model.*;
import model.Shape;
import model.dialogs.DialogProvider;
import model.interfaces.*;
import model.Commands.*;
import controller.DrawStrategies.*;
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

    private AppStateHandler stateHandler;

    private PaintCanvasBase paintCanvas;

    private ShapeListBuilder shapeListBuilder;

    String nameCounter;

    private ArrayList<IShapeItem> shapes;

    private ArrayList<Shape> selected;
    private ArrayList<Shape> clipboard;

    public ApplicationState(IUiModule uiModule, PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
        this.uiModule = uiModule;
        this.dialogProvider = new DialogProvider(this);
        setDefaults();


        //this.shapes = new ArrayList<>();


        this.selected = new ArrayList<>();
        this.clipboard = new ArrayList<>();
    }

    public void setStateHandler(AppStateHandler stateHandler){
        this.stateHandler = stateHandler;
    }

    public void copy() {
        ICommand command = new CopyCommand(stateHandler);
        try{command.run();} catch (IOException x) {System.out.println("IOException with copy.");}
        this.drawShapes();
    }

    public void paste() {
        ICommand command = new PasteCommand(stateHandler);
        try{command.run();} catch (IOException x) {System.out.println("IOException with paste.");}
        this.drawShapes();
    }

    public void delete() {
        ICommand command = new DeleteCommand(stateHandler);
        try{command.run();} catch (IOException x) {System.out.println("IOException with delete.");}
        this.drawShapes();
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
        this.drawShapes();
    }

    public ArrayList<Shape> getShapes(){
        return shapes;
    }

    public void setShapes(ArrayList<Shape> s){
        this.shapes = s;
        this.drawShapes();
    }

    public ArrayList<Shape> getClipboard(){
        return clipboard;
    }

    public void addShape(Shape shape){
        shapes.add(shape);
        this.drawShapes();

    }

    public void removeShape(Shape shape){
        shapes.remove(shape);
        this.drawShapes();
    }

    public void setClipboard(ArrayList<Shape> c){
        clipboard = c;
    }

    public void drawShapes() {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();

        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(0, 0, paintCanvas.getWidth(), paintCanvas.getHeight());

        IDrawStrategy drawStrategy;

        boolean isSelected;
        Shape shape;
        while ( shapes.hasNext() ){
            shape = shape.getNext();
        }
        for (Shape shape : shapes) {
            if(selected.contains(shape)){
                switch (shape.getShade()) {
                    case FILLED_IN:
                        drawStrategy = new SelectedFilledStrategy();
                        break;
                    case OUTLINE:
                        drawStrategy = new SelectedOutlineStrategy();
                        break;
                    case OUTLINE_AND_FILLED_IN:
                        drawStrategy = new SelectedFilledOutlineStrategy();
                        break;
                    default:
                        continue;
                }
            }
            else {
                switch (shape.getShade()) {
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
