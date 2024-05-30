package Domain;

import static Domain.BuildingModePage.*;

public class BuildingModeController {
    private WelcomePage welcomePage;
    private Login loginPage;
    private BuildingModeMenu buildingModeMenu;
    private BuildingMode assemblyMenu;
    private BuildingModePage currentMode;

    private GameListScreen gameListScreen;
    private GameController gameController;

    private GameInfo gameInfo;

    public BuildingModeController(GameController gameController) {
        this.gameController = gameController;
        welcomePage = new WelcomePage(this);
        loginPage = new Login (this);
        buildingModeMenu = new BuildingModeMenu(this);
        assemblyMenu = new BuildingMode(this);
        currentMode = WELCOME;
        welcomePage.setVisible(true);
    }

    public void switchScreens() {
        switch (currentMode) {
            case LOGIN:
                welcomePage.getClip().stop();
                welcomePage.setVisible(false);
                loginPage.setVisible(true);
                currentMode = BUILDING_MODE_MENU;
                break;
            case BUILDING_MODE_MENU:
                loginPage.setVisible(false);
                buildingModeMenu.setVisible(true);
                break;
            case NEW_GAME:
                buildingModeMenu.setVisible(false);
                assemblyMenu.setVisible(true);
                currentMode = ASSEMBLY_MENU;
                break;
            case LOAD_GAME:
                gameListScreen = new GameListScreen(this);
                gameListScreen.setVisible(true);
                buildingModeMenu.setVisible(false);
                currentMode = GAME_LIST_SCREEN;
                break;
            case ASSEMBLY_MENU:
                buildingModeMenu.getClip().stop();
                buildingModeMenu.setVisible(false);
                assemblyMenu.setVisible(true);
                currentMode = FINISH;
                break;
            case FINISH:
                assemblyMenu.setVisible(false);
                currentMode = READY_FOR_GAME;
                gameController.switchModes();
                break;
        }
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public void setCurrentMode(BuildingModePage currentMode) {
        this.currentMode = currentMode;
    }

    public BuildingModePage getCurrentMode() {
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


