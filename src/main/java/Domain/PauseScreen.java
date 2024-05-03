package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class PauseScreen extends JFrame {
    private JButton helpButton;
    private JButton saveButton;
    private JButton exitButton;
    private BuildingModeController buildingModeController;

    private boolean isSaveClicked = false;

    private  GameView gameView;
    private JButton returnMenuButton;
    public PauseScreen(GameWindow gameWindow, BuildingModeController buildingModeController, GameView gameView) {
        super("Pause");
        this.buildingModeController = buildingModeController;
        this.gameView = gameView;

        setSize(250,150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        helpButton = new JButton("Help");
        saveButton = new JButton("Save");
        exitButton = new JButton("Exit");
        returnMenuButton = new JButton("Return to Menu");


        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PauseScreen.this, "Help window will be opened", "Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameView.isGameRunning() && !gameWindow.isGameSaved()) {
                    JOptionPane.showMessageDialog(PauseScreen.this, "Game Saved", "Save", JOptionPane.INFORMATION_MESSAGE);
                    gameWindow.handleSaveAction();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(PauseScreen.this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        returnMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PauseScreen.this, "Building Mode Menu will be opened", "Menu", JOptionPane.INFORMATION_MESSAGE);


            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.add(helpButton);

        buttonPanel.add(saveButton);

        buttonPanel.add(exitButton);
        buttonPanel.add(returnMenuButton);
        add(buttonPanel);


        setLocationRelativeTo(null);
    }


    public  void closePauseScreen() {
        this.dispose(); //
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(() -> {
    //    PauseScreen pauseScreen = new PauseScreen();
    //    pauseScreen.setVisible(true);
    //});
    // }


}