package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame {
    private BuildingModeController buildingModeController;

    public WelcomePage(BuildingModeController buildingModeController) {
        this.buildingModeController = buildingModeController;

        setTitle("Lance of Destiny");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Lance of Destiny", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setPreferredSize(new Dimension(800, 100));
        add(titleLabel, BorderLayout.NORTH);

        JLabel welcomeLabel = new JLabel("Welcome to Lance of Destiny!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        welcomeLabel.setPreferredSize(new Dimension(800, 50));
        add(welcomeLabel, BorderLayout.CENTER);

        JButton loginButton = new JButton("Start");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildingModeController.setCurrentMode("login");
                buildingModeController.switchScreens();
            }
        });
        add(loginButton, BorderLayout.SOUTH);
    }

    /**
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BuildingModeController buildingModeController = new BuildingModeController();
            Domain.WelcomePage welcomePage = new Domain.WelcomePage(buildingModeController);
        });
    }
     **/
}