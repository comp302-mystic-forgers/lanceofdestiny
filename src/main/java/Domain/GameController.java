package Domain;

import static Domain.BuildingModePage.*;
import static Domain.RunningModePage.*;

public class GameController {

    private BuildingModeController buildingModeController;

    private GameWindow gameWindow;
    private RunningModePage currentMode;


    public GameController() {
        buildingModeController = new BuildingModeController(this);
        currentMode = BUILDING_MODE;
    }

    public void switchModes() {
        if(buildingModeController.getCurrentMode() == READY_FOR_GAME) {
            setCurrentMode(RUNNING);
            if (buildingModeController.isNewGame()) {
                gameWindow = new GameWindow(buildingModeController, null);
            } else {
                gameWindow = new GameWindow(buildingModeController, buildingModeController.getGameInfo());
            }
            gameWindow.setVisible(true);
        }
        if(currentMode == BUILDING_MODE){
            buildingModeController.setCurrentMode(BUILDING_MODE_MENU);
        }
    }

    public void setCurrentMode(RunningModePage mode) {
        currentMode = mode;
    }

    public RunningModePage getCurrentMode() {
        return currentMode;
    }

    public BuildingModeController getBuildingModeController() {
        return buildingModeController;
    }

    public static void main(String[] args){
        new GameController();
    }
}


