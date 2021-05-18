package model.interfaces;

import model.Enums.ShapeColor;
import model.Enums.ShapeShadingType;
import model.Enums.ShapeType;
import model.Enums.MouseMode;
import view.interfaces.IDialogChoice;

public interface IDialogProvider {

    IDialogChoice<ShapeType> getChooseShapeDialog();

    IDialogChoice<ShapeColor> getChoosePrimaryColorDialog();

    IDialogChoice<ShapeColor> getChooseSecondaryColorDialog();

    IDialogChoice<ShapeShadingType> getChooseShadingTypeDialog();

    IDialogChoice<MouseMode> getChooseStartAndEndPointModeDialog();
}
