package Domain;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {
    private PauseScreen pauseScreen;
    private BuildingModeController buildingModeController;
    private boolean gamePaused;
    private GameView GameView;
    private JButton pauseButton;
    public GameWindow() {
        super("Lance of Destiny");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        GameView = new GameView(getWidth(), getHeight());
        add(GameView);
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gamePaused) {
                    pauseGame();
                } else {
                    resumeGame();
                }
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(pauseButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Key bindings for moving and rotating the staff
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    GameView.moveStaff(e.getKeyCode());
                } else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
                    GameView.rotateStaff(e.getKeyCode());
                }
            }
        });
        setVisible(true);
    }
    private void pauseGame() {
        gamePaused = true;
        pauseButton.setText("Resume");
        GameView.getTimer().stop(); // Stop the game loop using the timer from GameView
        //PauseScreen pauseScreen = new PauseScreen(this); // Pass reference of GameWindow to PauseScreen
        PauseScreen pauseScreen = new PauseScreen(this, buildingModeController);

        pauseScreen.setVisible(true);
    }

    public void resumeGame() {

        gamePaused = false;
        pauseButton.setText("Pause");
        GameView.getTimer().start(); // Resume the game loop using the timer from GameView
        if (pauseScreen != null) {
            pauseScreen.closePauseScreen(); // Close the pause screen if it exists
        }
    }

    public static void main(String[] args) {

        new GameWindow();
    }
}

