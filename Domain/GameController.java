package Domain;
import java.awt.*;

public class GameController {

    private BuildingModeController buildingModeController;

    private GameWindow gameWindow;
    private String currentMode;



    public GameController() {
        buildingModeController = new BuildingModeController(this);
        currentMode = "building_mode";
    }

    public void switchModes() {
        if(buildingModeController.getCurrentMode() == "readyForGame"){
            setCurrentMode("running");
            gameWindow = new GameWindow();
            gameWindow.setVisible(true);
        }
    }

    public void setCurrentMode(String mode) {
        currentMode = mode;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public static void main(String[] args){
        new GameController();
    }
}


