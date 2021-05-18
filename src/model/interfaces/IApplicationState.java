package model.interfaces;

import model.Enums.ShapeColor;
import model.Enums.ShapeShadingType;
import model.Enums.ShapeType;
import model.Enums.MouseMode;

public interface IApplicationState {
    void setActiveShape();

    void setActivePrimaryColor();

    void setActiveSecondaryColor();

    void setActiveShadingType();

    void setActiveStartAndEndPointMode();

    ShapeType getActiveShapeType();

    ShapeColor getActivePrimaryColor();

    ShapeColor getActiveSecondaryColor();

    ShapeShadingType getActiveShapeShadingType();

    MouseMode getActiveMouseMode();

    void UNDO();

    void REDO();

}
