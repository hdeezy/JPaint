package controller;

import model.interfaces.*;
import model.persistence.AppStateHandler;
import model.persistence.ApplicationState;
import controller.mouse.IMouseListener;
import view.EventName;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;

public class JPaintController implements IJPaintController, IStateObserver {
    private IUiModule uiModule;
    private ApplicationState applicationState;
    private IMouseListener mouse;
    private AppStateHandler stateHandler;
    private PaintCanvasBase paintCanvas;

    public JPaintController(IUiModule uiModule, PaintCanvasBase paintCanvas) {
        this.uiModule = uiModule;
        this.applicationState = new ApplicationState(uiModule, paintCanvas);
        AppStateHandler stateHandler = new AppStateHandler(applicationState);
        this.mouse = new IMouseListener(applicationState, stateHandler);
        this.paintCanvas = paintCanvas;
        this.paintCanvas.addMouseListener(mouse);
        stateHandler.registerObserver(this);
        stateHandler.registerObserver(mouse);
        applicationState.setStateHandler(stateHandler);
    }

    @Override
    public void setup() {
        setupEvents();
    }

    private void setupEvents() {
        uiModule.addEvent(EventName.CHOOSE_SHAPE, () -> applicationState.setActiveShape());
        uiModule.addEvent(EventName.CHOOSE_PRIMARY_COLOR, () -> applicationState.setActivePrimaryColor());
        uiModule.addEvent(EventName.CHOOSE_SECONDARY_COLOR, () -> applicationState.setActiveSecondaryColor());
        uiModule.addEvent(EventName.CHOOSE_SHADING_TYPE, () -> applicationState.setActiveShadingType());
        uiModule.addEvent(EventName.CHOOSE_MOUSE_MODE, () -> applicationState.setActiveStartAndEndPointMode());
        uiModule.addEvent(EventName.UNDO, () -> applicationState.UNDO());
        uiModule.addEvent(EventName.REDO, () -> applicationState.REDO());
        uiModule.addEvent(EventName.COPY, () -> applicationState.copy());
        uiModule.addEvent(EventName.PASTE, () -> applicationState.paste());
        uiModule.addEvent(EventName.DELETE, () -> applicationState.delete());
        uiModule.addEvent(EventName.GROUP, () -> applicationState.group());
        uiModule.addEvent(EventName.UNGROUP, () -> applicationState.ungroup());

    }


    public ApplicationState getApplicationState(){return this.applicationState;}
    public IMouseListener getMouse(){return this.mouse;}
    public PaintCanvasBase getPaintCanvas(){return this.paintCanvas;}

    @Override
    public void update(ApplicationState AppState) {
        this.applicationState = AppState;
    }
}
