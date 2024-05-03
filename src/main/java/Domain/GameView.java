package Domain;



import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameView extends JPanel implements ComponentListener, ActionListener {
    private MagicalStaff magicalStaff;
    private FireBall fireball;
    private ArrayList<SimpleBarrier> simpleBarriers; // ArrayList to hold SimpleBarrier objects
    private ArrayList<ReinforcedBarrier> reinforcedBarriers;
    private ArrayList<ExplosiveBarrier> explosiveBarriers;
    private ArrayList<RewardingBarrier> rewardingBarriers;
    private OverwhelmingFireBall overFireBall;
    private MagicalStaffExp magicalStaffExp;
    private Timer timer;
    private boolean gameRunning = true;

    private  PauseScreen pauseScreen;
    private BufferedImage background;
    private BufferedImage simpleBarrierImage;
    private BufferedImage firmBarrierImage;
    private BufferedImage explosiveBarrierImage;
    private BufferedImage giftBarrierImage;
    //private GiftTaking giftWindow;
    private PlayerAccount currentPlayer;

    private  PlayerAccountDAO playerAccountDAO;
    private final GameInfoDAO gameInfoDAO;

    public GameView(int panelWidth, int panelHeight, GameInfoDAO gameInfoDAO, PlayerAccountDAO playerAccountDAO) {
        super();
        this.gameInfoDAO =gameInfoDAO;
        this.playerAccountDAO = playerAccountDAO;
        this.currentPlayer = UserSession.getInstance().getCurrentPlayer();
        try {
            InputStream inputStream = getClass().getResourceAsStream("Assets/Images/200Background.png");
            if (inputStream == null) {
                throw new IOException("Image not found.");
            }
            background = ImageIO.read(inputStream);
            simpleBarrierImage = ImageIO.read(new File("Assets/Images/200Bluegem.png"));
            firmBarrierImage = ImageIO.read(new File("Assets/Images/200Firm.png"));
            explosiveBarrierImage = ImageIO.read(new File("Assets/Images/200Redgem.png"));
            giftBarrierImage = ImageIO.read(new File("Assets/Images/200Greengem.png"));
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
        this.magicalStaff = new MagicalStaff(panelWidth, panelHeight); // Position MagicalStaff towards the bottom
        this.fireball = new FireBall(magicalStaff.getX() + magicalStaff.getWidth()/3,magicalStaff.getY() - 4 * magicalStaff.getHeight()); // Start Fireball from the top middle
        this.simpleBarriers = new ArrayList<>(); // Initialize the ArrayList
        this.reinforcedBarriers = new ArrayList<>();
        this.explosiveBarriers = new ArrayList<>();
        this.rewardingBarriers = new ArrayList<>();
        this.overFireBall = new OverwhelmingFireBall(fireball);
        this.magicalStaffExp = new MagicalStaffExp(magicalStaff);
        //this.giftWindow = new GiftTaking();
        addComponentListener(this);

        int count = GameLayoutPanel.placedBarriers.size();

        for (int i = 0; i < count; i++) {
            Rectangle rectangle = GameLayoutPanel.placedBarriers.get(i);
            Integer type = GameLayoutPanel.placedBarrierTypes.get(i);

            if (type == 1) {
                simpleBarriers.add(new SimpleBarrier((int) rectangle.getX()/6*8, (int) rectangle.getY()/6*8, (int) rectangle.getWidth()/6*8, (int) rectangle.getHeight()/6*8));
            } else if (type == 2) {
                reinforcedBarriers.add(new ReinforcedBarrier((int) rectangle.getX()/6*8, (int) rectangle.getY()/6*8, (int) rectangle.getWidth()/6*8, (int) rectangle.getHeight()/6*8));
            } else if (type == 3) {
                explosiveBarriers.add(new ExplosiveBarrier((int) rectangle.getX()/6*8, (int) rectangle.getY()/6*8, (int) rectangle.getWidth()/6*8, (int) rectangle.getHeight()/6*8));
            } else {
                rewardingBarriers.add(new RewardingBarrier((int) rectangle.getX()/6*8, (int) rectangle.getY()/6*8, (int) rectangle.getWidth()/6*8, (int) rectangle.getHeight()/6*8));
            }
        }
        timer = new Timer(10, this);
        timer.start();
    }
    void saveGameInfo() {
        GameInfo gameInfo = new GameInfo();
        PlayerAccount playerAccount = playerAccountDAO.findPlayerAccountByUsername(currentPlayer.getUsername());
        gameInfo.getPlayer().setPlayerId(playerAccount.getPlayerId());
        gameInfo.getPlayer().setUsername(playerAccount.getUsername());
        gameInfo.setScore(1000);
        gameInfo.setLives(3);
        gameInfo.setGameState(GameState.PASSIVE);
        gameInfo.setLastSaved(new Date());
        gameInfo.setSpellsAcquired(null);
        List<Barrier> remainingBarriers = new ArrayList<>();
        remainingBarriers.addAll(reinforcedBarriers);
        remainingBarriers.addAll(simpleBarriers);
        remainingBarriers.addAll(explosiveBarriers);
        remainingBarriers.addAll(rewardingBarriers);
        gameInfo.setBarriersRemaining(remainingBarriers);
        try {
            gameInfoDAO.saveGameInfo(gameInfo);
        } catch (Exception e) {
            System.err.println("Error saving game info: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameRunning) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            magicalStaff.draw(g);

            fireball.draw(g);
            // Draw all SimpleBarrier objects in the ArrayList

            for (SimpleBarrier barrier : simpleBarriers) {
                barrier.draw(g);
            }
            for (ReinforcedBarrier rbarrier : reinforcedBarriers) {
                rbarrier.draw(g);
            }
            for (ExplosiveBarrier ebarrier : explosiveBarriers) {
                ebarrier.draw(g);
            }
            for (RewardingBarrier rwbarrier : rewardingBarriers) {
                rwbarrier.draw(g);
            }
        } else if (pauseScreen.isSaveClicked()) {
            saveGameInfo();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            if (fireball.isBallActive) {
                fireball.move(getWidth(), getHeight());
                checkCollisions();
                repaint();
            }
        }
    }

    private void checkCollisions() {
        if (fireball.getX() <= 0 || fireball.getX() + fireball.getDiameter() >= getWidth()) {
            fireball.reverseXDirection();
        }

        if (fireball.getY() <= 0) {
            fireball.reverseYDirection();
        }

        if (fireball.collidesWithMagicalStaff(magicalStaff)) {
            double speed = Math.hypot(fireball.xVelocity, fireball.yVelocity);

            // Angle of the staff in radians
            double staffAngleRadians = Math.toRadians(magicalStaff.getAngle());

            // Normal vector to the staff surface
            double normalX = Math.cos(Math.PI / 2 + staffAngleRadians);
            double normalY = Math.sin(Math.PI / 2 + staffAngleRadians);

            // Incident vector is just the velocity vector of the fireball
            double incidentX = fireball.xVelocity;
            double incidentY = fireball.yVelocity;

            // Dot product of incident vector and the normal vector
            double dotProduct = incidentX * normalX + incidentY * normalY;

            // Reflection vector calculation
            double reflectionX = incidentX - 2 * dotProduct * normalX;
            double reflectionY = incidentY - 2 * dotProduct * normalY;

            // Normalize the reflection vector and scale it by the original speed
            double reflectionMagnitude = Math.hypot(reflectionX, reflectionY);
            fireball.xVelocity = (reflectionX / reflectionMagnitude) * speed;
            fireball.yVelocity = (reflectionY / reflectionMagnitude) * speed;

            // Ensure the fireball bounces away from the staff correctly
            fireball.yVelocity = -Math.abs(fireball.yVelocity); // This ensures it always moves away from the staff
        }





        // Game Over condition: Fireball falls below the Magical Staff
        if (fireball.getY() + fireball.getDiameter() > magicalStaff.getY() + magicalStaff.getHeight()) {
            fireball.isBallActive = false;
            gameRunning = false;
            timer.stop(); // Stop the game loop
            JOptionPane.showMessageDialog(this, "Game Over", "End", JOptionPane.INFORMATION_MESSAGE);
            // Further actions to reset or end the game can be added here
        }


        for (SimpleBarrier barrier : simpleBarriers) {
            if (barrier.collidesWithFireBall(fireball)) {
                if(overFireBall.isActivated()){
                    overFireBall.handleCollisionResponse(barrier);
                }
                else{
                    barrier.destroy();
                    barrier.handleCollisionResponse(fireball);
                }
            }
        }

        for (ReinforcedBarrier rbarrier : reinforcedBarriers) {
            if (rbarrier.collidesWithFireBall(fireball)) {
                if(overFireBall.isActivated()){
                    overFireBall.handleCollisionResponse(rbarrier);
                }
                else{
                    rbarrier.isDestroyed();
                    rbarrier.handleCollisionResponse(fireball);
                }
            }
        }

        for (ExplosiveBarrier ebarrier : explosiveBarriers) {
            if (ebarrier.collidesWithFireBall(fireball)) {
                if(overFireBall.isActivated()){
                    overFireBall.handleCollisionResponse(ebarrier);
                }
                else {
                    ebarrier.destroy();
                    ebarrier.handleCollisionResponse(fireball);
                }
            }
            if(ebarrier.destroyed) {
                if (ebarrier.getY() + ebarrier.getHeight() > magicalStaff.getY() &&
                        ebarrier.getX() + ebarrier.getWidth() > magicalStaff.getX() &&
                        ebarrier.getX() < magicalStaff.getX() + magicalStaff.getWidth()) {
                    gameRunning = false;
                    timer.stop(); // Stop the game loop
                    JOptionPane.showMessageDialog(this, "Game Over", "End", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        for (RewardingBarrier rwbarrier : rewardingBarriers) {
            if (rwbarrier.collidesWithFireBall(fireball)) {
                if (overFireBall.isActivated()){
                    overFireBall.handleCollisionResponse(rwbarrier);
                }
                else{
                    rwbarrier.destroy();
                    rwbarrier.handleCollisionResponse(fireball);
                }
            }
            if(rwbarrier.destroyed) {
                if (rwbarrier.getY() + rwbarrier.getHeight() > magicalStaff.getY() &&
                        rwbarrier.getX() + rwbarrier.getWidth() > magicalStaff.getX() &&
                        rwbarrier.getX() < magicalStaff.getX() + magicalStaff.getWidth()){
                    magicalStaffExp.activate();
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - magicalStaffExp.getTime() > 30 * 10) {
                        magicalStaffExp.deactivate();
                    }

                    /*overFireBall.activate();
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - overFireBall.getTime() > 30 * 10) {
                        overFireBall.deactivate();
                    }*/
                }
            }
        }
    }

    public void moveStaff(int keyCode, int type) {
        if (gameRunning && fireball.isBallActive) {
            magicalStaff.move(keyCode, getWidth(), type);
            repaint();
        }
    }

    public void rotateStaff(int keyCode) {
        if (gameRunning && fireball.isBallActive) {
            magicalStaff.rotate(keyCode);
            repaint();
        }
    }

    public void resetStaff(int keyCode) {
        if (keyCode == KeyEvent.VK_A) {
            while (magicalStaff.getAngle() < 0) {
                magicalStaff.resetRotation(keyCode);
                repaint();
            }
        } else if (keyCode == KeyEvent.VK_D) {
                while (magicalStaff.getAngle() > 0) {
                    magicalStaff.resetRotation(keyCode);
                    repaint();
                }
        }
    }

    public void throwBall(int keyCode) {
        if (gameRunning) {
            fireball.ballThrower(keyCode);
            repaint();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        magicalStaff.updatePosition(getWidth(), getHeight());
        repaint();
    }
    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
    public Timer getTimer() {
        return timer;
    }


}
