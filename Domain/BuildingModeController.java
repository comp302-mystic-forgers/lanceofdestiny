package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildingModeController {
    private WelcomePage welcomePage;
    private Login loginPage;
    private BuildingModeMenu buildingModeMenu;
    private BuildingMode assemblyMenu;
    private String currentMode;

    private GameController gameController;

    public BuildingModeController(GameController gameController) {
        this.gameController = gameController;
        welcomePage = new WelcomePage(this);
        loginPage = new Login (this);
        buildingModeMenu = new BuildingModeMenu(this);
        assemblyMenu = new BuildingMode(this);
        currentMode = "welcome";
        welcomePage.setVisible(true);
    }

    public void switchScreens() {
        switch (currentMode) {
            case "login":
                welcomePage.setVisible(false);
                loginPage.setVisible(true);
                currentMode = "building_mode_menu";
                break;
            case "building_mode_menu":
                loginPage.setVisible(false);
                buildingModeMenu.setVisible(true);
                currentMode = "assembly_menu";
                break;
            case "assembly_menu":
                buildingModeMenu.setVisible(false);
                assemblyMenu.setVisible(true);
                currentMode = "finish";
                break;
            case "finish":
                assemblyMenu.setVisible(false);
                currentMode = "readyForGame";
                gameController.switchModes();
                break;
        }
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public WelcomePage getWelcomePage() {
        return welcomePage;
    }

    public Login getLogin() {
        return loginPage;
    }

    public BuildingModeMenu getBuildingModeMenu() {
        return buildingModeMenu;
    }

    public BuildingMode getBuidlingMode() {
        return assemblyMenu;
    }

}


