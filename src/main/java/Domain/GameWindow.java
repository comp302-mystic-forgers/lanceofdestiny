package Domain;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class GameWindow extends JFrame {
    private PauseScreen pauseScreen;
    private BuildingModeController buildingModeController;
    private boolean gamePaused;
    private GameView GameView;
    private JButton pauseButton;
    private GameInfoDAO gameInfoDAO;
    private PlayerAccountDAO playerAccountDAO;
    private boolean isGameSaved = false;
    public GameWindow() {
        super("Lance of Destiny");
        setSize(1280, 720);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Database connection = new Database();
        gameInfoDAO = new GameInfoDAO(connection);
        playerAccountDAO = new PlayerAccountDAO(connection);
        GameView = new GameView(getWidth(), getHeight(), gameInfoDAO, playerAccountDAO);
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

        keyListener listener = new keyListener();
        addKeyListener(listener);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    class keyListener extends KeyAdapter {
        private Timer actionTimer;
        protected boolean lPressed = false;
        protected boolean rPressed = false;
        protected boolean APressed = false;
        protected boolean DPressed = false;

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                lPressed = true;
                GameView.moveStaff(e.getKeyCode(), 0);
                KeyHoldController();
                actionTimer.start();
            }  else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rPressed = true;
                GameView.moveStaff(e.getKeyCode(), 0);
                KeyHoldController();
                actionTimer.start();
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                APressed = true;
                GameView.rotateStaff(e.getKeyCode());
                KeyHoldController();
                actionTimer.start();
            } else if (e.getKeyCode() ==  KeyEvent.VK_D) {
                DPressed = true;
                GameView.rotateStaff(e.getKeyCode());
                KeyHoldController();
                actionTimer.start();
            }  else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                GameView.throwBall(e.getKeyCode());
            }
        }

        public void KeyHoldController() {
            actionTimer = new Timer(10, e-> {
                if (lPressed) {
                    GameView.moveStaff(KeyEvent.VK_LEFT, 1);
                } else if (rPressed) {
                    GameView.moveStaff(KeyEvent.VK_RIGHT, 1);
                } else if (APressed) {
                    GameView.rotateStaff(KeyEvent.VK_A);
                } else if (DPressed) {
                    GameView.rotateStaff(KeyEvent.VK_D);
                }

            });
            actionTimer.setRepeats(true);
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                lPressed = false;
                actionTimer.stop();
                actionTimer = null;
                GameView.moveStaff(e.getKeyCode(), 0);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rPressed = false;
                actionTimer.stop();
                actionTimer = null;
                GameView.moveStaff(e.getKeyCode(), 0);
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                APressed = false;
                actionTimer.stop();
                actionTimer = null;
                GameView.resetStaff(e.getKeyCode());
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                DPressed = false;
                actionTimer.stop();
                actionTimer = null;
                GameView.resetStaff(e.getKeyCode());
            }
        }

    }
    private void pauseGame() {
        gamePaused = true;
        pauseButton.setText("Resume");
        GameView.getTimer().stop(); // Stop the game loop using the timer from GameView
        //PauseScreen pauseScreen = new PauseScreen(this); // Pass reference of GameWindow to PauseScreen
        PauseScreen pauseScreen = new PauseScreen(this, buildingModeController, GameView);
        pauseScreen.setVisible(true);
    }

    public void resumeGame() {
        gamePaused = false;
        pauseButton.setText("Pause");
        GameView.getTimer().start(); // Resume the game loop using the timer from GameView
        if (pauseScreen != null) {
            pauseScreen.closePauseScreen(); // Close the pause screen if it exists
        }
        this.requestFocusInWindow();
        isGameSaved = false;
    }
    public void handleSaveAction() {
        if (GameView != null) {
            GameView.saveGameInfo();
            isGameSaved = true;
        }
    }


    public boolean isGameSaved() {
        return isGameSaved;
    }

    public static void main(String[] args) {
        new GameWindow();
    }
}

