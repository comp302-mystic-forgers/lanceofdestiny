package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class PauseScreen extends JFrame {
    private JButton helpButton;
    private JButton saveButton;
    private JButton exitButton;
    private BuildingModeController buildingModeController;


    private JButton returnMenuButton;
    public PauseScreen(GameWindow gameWindow, BuildingModeController buildingModeController) {
        super("Pause");
        this.buildingModeController = buildingModeController;



        setSize(250,150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        helpButton = new JButton("Help");
        saveButton = new JButton("Save");
        exitButton = new JButton("Exit");
        returnMenuButton = new JButton("Return Menu");


        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayHelpFile();
                }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PauseScreen.this, "Game Saved", "Save", JOptionPane.INFORMATION_MESSAGE);

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
                 GameController gameController = new GameController();
                BuildingModeController buildingModeController1 = new BuildingModeController(gameController);

                  //buildingModeController.setCurrentMode("building_mode_menu");
                //buildingModeController.setCurrentMode("building_mode_menu");

               BuildingModeMenu menu = new BuildingModeMenu(buildingModeController1);

                menu.setVisible(false);
               // buildingModeController.setCurrentMode("building_mode_menu");
              //  buildingModeController.switchScreens();
               gameWindow.dispose();
               // closePauseScreen();

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
    private void displayHelpFile() {
        JFrame helpFrame = new JFrame("Help Contents");
        helpFrame.setSize(300, 400);
        helpFrame.setLocationRelativeTo(this);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        helpFrame.add(scrollPane);

        try {
            File file = new File("Assets/Texts/Help.txt"); // Make sure to provide the correct path to your text file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            textArea.read(reader, null);
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            textArea.setText("Error loading help file!");
        }

        helpFrame.setVisible(true);
    }


    public  void closePauseScreen() {
        this.dispose();
    }
    // public static void main(String[] args) {
     //SwingUtilities.invokeLater(() -> {
       // PauseScreen pauseScreen = new PauseScreen();
      //  pauseScreen.setVisible(true);
    //});
     //}


}