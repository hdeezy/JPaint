package model.persistence;

import model.*;
import model.Shape;
import model.dialogs.DialogProvider;
import model.interfaces.*;
import model.Commands.*;
import controller.DrawStrategies.*;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static model.ShapeShadingType.*;

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

    String nameCounter;

    private ArrayList<IShapeItem> shapes;

    private ArrayList<IShapeItem> selected;
    private ArrayList<IShapeItem> clipboard;


    public ApplicationState(IUiModule uiModule, PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
        this.uiModule = uiModule;
        this.dialogProvider = new DialogProvider(this);
        setDefaults();


        this.shapes = new ArrayList<>();
        this.selected = new ArrayList<>();
        this.clipboard = new ArrayList<>();
    }

    public void setStateHandler(AppStateHandler stateHandler){
        this.stateHandler = stateHandler;
    }

    public void group(){
        ICommand command = new GroupCommand(stateHandler);
        try{command.run();} catch (IOException x) {System.out.println("IOException with group.");}
        this.drawShapes();
    }

    public void ungroup(){
        ICommand command = new UngroupCommand(stateHandler);
        try{command.run();} catch (IOException x) {System.out.println("IOException with ungroup.");}
        this.drawShapes();
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

    public ArrayList<IShapeItem> getSelected(){
        return selected;
    }

    public void setSelected(ArrayList<IShapeItem> s){
        this.selected = s;
        this.drawShapes();
    }

    public ArrayList<IShapeItem> getShapes(){
        return shapes;
    }

    public void setShapes(ArrayList<IShapeItem> s){
        this.shapes = s;
        this.drawShapes();
    }

    public ArrayList<IShapeItem> getClipboard(){
        return clipboard;
    }

    public void addShape(IShapeItem shape){
        shapes.add(shape);
        this.drawShapes();

    }

    public void removeShape(IShapeItem shape){
        shapes.remove(shape);
        this.drawShapes();
    }

    public void setClipboard(ArrayList<IShapeItem> c){
        clipboard = c;
    }

    public void drawShapeItem(IShapeItem shape, Graphics2D graphics2d){
            IDrawStrategy drawStrategy = null;

            if (shape.getClass().equals(Shape.class)) {
                if (selected.contains(shape)) {
                    switch (((Shape) shape).getShade()) {
                        case FILLED_IN:
                            drawStrategy = new SelectedFilledStrategy();
                            break;
                        case OUTLINE:
                            drawStrategy = new SelectedOutlineStrategy();
                            break;
                        case OUTLINE_AND_FILLED_IN:
                            drawStrategy = new SelectedFilledOutlineStrategy();
                            break;
                    }
                } else {
                    switch (((Shape) shape).getShade()) {
                        case FILLED_IN:
                            drawStrategy = new FilledStrategy();
                            break;
                        case OUTLINE:
                            drawStrategy = new OutlineStrategy();
                            break;
                        case OUTLINE_AND_FILLED_IN:
                            drawStrategy = new FilledOutlineStrategy();
                            break;
                    }
                }
                drawStrategy.draw((Shape)shape, paintCanvas);
            }
            // if there is a group, traverse it dispatching recursive calls to drawShapeItem for each group member
            else if (shape.getClass().equals(ShapeGroup.class)) {
                ArrayList<IShapeItem> group = ((ShapeGroup) shape).getShapes();
                Point topLeft = shape.getTopLeft();
                Point bottomRight = shape.getBottomRight();

                //selection shape
                int[] x2 = {topLeft.x-5, topLeft.x-5, bottomRight.x+10};
                int[] y2 = {topLeft.y+10, bottomRight.y-5, bottomRight.y-5};
                graphics2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[]{9}, 0));
                graphics2d.setColor(Color.BLACK);
                graphics2d.drawRect(topLeft.x-5, topLeft.y-abs(bottomRight.y-topLeft.y)-5,bottomRight.x-topLeft.x+10, abs(bottomRight.y-topLeft.y)+10);

                for(IShapeItem item : group){
                    drawShapeItem(item, graphics2d);
                }
            }
            // dealing with the composition externally gives a bad code smell, but it is needed given the implementation of
            // drawing the shapes
    }



    public void drawShapes() {
        Graphics2D graphics2d = paintCanvas.getGraphics2D();

        // blank out canvas before redrawing everything
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(0, 0, paintCanvas.getWidth(), paintCanvas.getHeight());

        // dispatch each element to be dealt with appropriately
        for (IShapeItem shape : shapes) {
                drawShapeItem(shape, graphics2d);
            }
    }

    private void setDefaults() {
        activeShapeType = ShapeType.RECTANGLE;
        activePrimaryColor = ShapeColor.BLUE;
        activeSecondaryColor = ShapeColor.GREEN;
        activeShapeShadingType = FILLED_IN;
        activeMouseMode = MouseMode.DRAW;
    }


}
