package Domain;

import static Domain.BuildingModePage.READY_FOR_GAME;

public class GameController {

    private BuildingModeController buildingModeController;

    private GameWindow gameWindow;
    private String currentMode;


    public GameController() {
        buildingModeController = new BuildingModeController(this);
        currentMode = "building_mode";
    }

    public void switchModes() {
        if(buildingModeController.getCurrentMode() == READY_FOR_GAME){
            setCurrentMode("running");
            gameWindow = new GameWindow(buildingModeController, buildingModeController.getGameInfo());
            gameWindow.setVisible(true);
        }
    }

    public void setCurrentMode(String mode) {
        currentMode = mode;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public BuildingModeController getBuildingModeController() {
        return buildingModeController;
    }

    public static void main(String[] args){
        new GameController();
    }
}


