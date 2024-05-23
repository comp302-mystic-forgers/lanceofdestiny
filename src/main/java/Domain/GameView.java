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
import java.util.ArrayList; // Import ArrayList class
import java.util.Date;
import java.util.List;

public class GameView extends JPanel implements ComponentListener, ActionListener {
    private MagicalStaff magicalStaff;
    private FireBall fireball;
    private ArrayList<SimpleBarrier> simpleBarriers; // ArrayList to hold SimpleBarrier objects
    private ArrayList<ReinforcedBarrier> reinforcedBarriers;
    private ArrayList<ExplosiveBarrier> explosiveBarriers;
    private ArrayList<RewardingBarrier> rewardingBarriers;
    private List<Spell> collectedSpells;
    private OverwhelmingFireBall overFireBall;
    private MagicalStaffExp magicalStaffExp;
    private FelixFelicis felixFelicis;
    private Timer timer;
    private boolean gameRunning = true;
    private  PauseScreen pauseScreen;
    private BufferedImage background;
    private BufferedImage simpleBarrierImage;
    private BufferedImage firmBarrierImage;
    private BufferedImage explosiveBarrierImage;
    private BufferedImage giftBarrierImage;
    private PlayerAccount currentPlayer = UserSession.getInstance().getCurrentPlayer();
    private  PlayerAccountDAO playerAccountDAO;
    private final GameInfoDAO gameInfoDAO;
    private HUD hud;
    private Score score;
    private Hex hexSpell;

    public GameView(int panelWidth, int panelHeight, GameInfoDAO gameInfoDAO, PlayerAccountDAO playerAccountDAO) {
        super();
        this.gameInfoDAO =gameInfoDAO;
        this.playerAccountDAO = playerAccountDAO;
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
        this.magicalStaff = new MagicalStaff(panelWidth, panelHeight - 100); // Position MagicalStaff towards the bottom
        this.fireball = new FireBall(magicalStaff.getX() + magicalStaff.getWidth()/3,magicalStaff.getY() - magicalStaff.getHeight()/160, null); // Start Fireball from the top middle
        this.felixFelicis = new FelixFelicis(currentPlayer);
        this.simpleBarriers = new ArrayList<>(); // Initialize the ArrayList
        this.reinforcedBarriers = new ArrayList<>();
        this.explosiveBarriers = new ArrayList<>();
        this.rewardingBarriers = new ArrayList<>();
        this.collectedSpells = new ArrayList<>();
        this.overFireBall = new OverwhelmingFireBall(fireball);
        this.magicalStaffExp = new MagicalStaffExp(magicalStaff);
        this.felixFelicis = new FelixFelicis(currentPlayer);
        this.hexSpell = new Hex(magicalStaff);
        this.hud = new HUD();
        this.score = new Score();
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

        // Create multiple SimpleBarrier objects and add them to the ArrayList
//        simpleBarriers.add(new SimpleBarrier(100, 200, 50, 20));
//        simpleBarriers.add(new SimpleBarrier(300, 150, 50, 20));
//        reinforcedBarriers.add(new ReinforcedBarrier(400, 100, 50, 20));
//        reinforcedBarriers.add(new ReinforcedBarrier(300, 100, 50, 20));
//        explosiveBarriers.add(new ExplosiveBarrier(500, 100, 50, 15));
//        explosiveBarriers.add(new ExplosiveBarrier(600, 100, 50, 15));
//        rewardingBarriers.add(new RewardingBarrier(700, 100, 50, 20));
//        rewardingBarriers.add(new RewardingBarrier(800, 100, 50, 20));


        timer = new Timer(10, this);
        timer.start();
    }
    void saveGameInfo() {
        GameInfo gameInfo = new GameInfo();
        PlayerAccount playerAccount = playerAccountDAO.findPlayerAccountByUsername(currentPlayer.getUsername());
        gameInfo.getPlayer().setPlayerId(playerAccount.getPlayerId());
        gameInfo.getPlayer().setUsername(playerAccount.getUsername());
        gameInfo.setScore((int) score.getScoreValue());
        gameInfo.setLives(currentPlayer.getChances());
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

    public long getScore() {
        return score.getScoreValue();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameRunning) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            magicalStaff.draw(g);

            fireball.draw(g);
            // Draw all SimpleBarrier objects in the ArrayList

            for (FireBall hex : magicalStaff.getHexes()) {
                hex.draw(g);
            }
            add(hud.getLivesLabel(), BorderLayout.NORTH);
            add(hud.getScoreLabel(), BorderLayout.NORTH);

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
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            if (fireball.isBallActive) {
                fireball.move(getWidth(), getHeight());
                checkCollisions();
                updateGifts();
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
            updateLives();
            // Further actions to reset or end the game can be added here
        }


        for (SimpleBarrier barrier : simpleBarriers) {
            if (barrier.collidesWithFireBall(fireball)) {
                if(overFireBall.isActivated()){
                    overFireBall.handleCollisionResponse(barrier);
                }
                else{
                    updateScore();
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
                    if(rbarrier.isDestroyed()){
                        updateScore();
                    }
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
                    updateScore();
                    ebarrier.destroy();
                    ebarrier.handleCollisionResponse(fireball);
                }
            }
            if(ebarrier.destroyed) {
                if (ebarrier.getY() + ebarrier.getHeight() > magicalStaff.getY() &&
                        ebarrier.getX() + ebarrier.getWidth() > magicalStaff.getX() &&
                        ebarrier.getX() < magicalStaff.getX() + magicalStaff.getWidth() && !ebarrier.isHitStaff()) {
                    updateLives();
                    ebarrier.staffHit();
                }
            }
        }

        for (RewardingBarrier rwbarrier : rewardingBarriers) {
            if (rwbarrier.collidesWithFireBall(fireball)) {
                if (overFireBall.isActivated()){
                    overFireBall.handleCollisionResponse(rwbarrier);
                }
                else{
                    updateScore();
                    rwbarrier.destroy();
                    rwbarrier.handleCollisionResponse(fireball);
                }
            }
            if(rwbarrier.destroyed) {
                if (rwbarrier.getY() + rwbarrier.getHeight() > magicalStaff.getY() &&
                        rwbarrier.getX() + rwbarrier.getWidth() > magicalStaff.getX() &&
                        rwbarrier.getX() < magicalStaff.getX() + magicalStaff.getWidth()){

                    hexSpell.activate();
                    /*if(!felixFelicis.isActivated()){
                         felixFelicis.activate();
                         updateLives();
                      }*/
                    /*magicalStaffExp.activate();
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - magicalStaffExp.getTime() > 30 * 100) {
                        magicalStaffExp.deactivate();
                    }

                    overFireBall.activate();
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - overFireBall.getTime() > 30 * 10) {
                        overFireBall.deactivate();
                    }*/
                }
            }
        }

        ArrayList<FireBall> hexes = magicalStaff.getHexes();

        for (int i = 0; i < hexes.size(); i++) {
            FireBall hex = hexes.get(i);

            if (hex.getX() <= 0 || hex.getX() + hex.getDiameter() >= getWidth()) {
                hex.reverseXDirection();
            }

            if (hex.getY() <= 0) {
                hex.reverseYDirection();
            }

            if(hex.collidesWithMagicalStaff(magicalStaff)) {
                double speed = Math.hypot(hex.xVelocity, hex.yVelocity);

                double staffAngleRadians = Math.toRadians(magicalStaff.getAngle());

                double normalX = Math.cos(Math.PI / 2 + staffAngleRadians);
                double normalY = Math.sin(Math.PI / 2 + staffAngleRadians);

                double incidentX = hex.xVelocity;
                double incidentY = hex.yVelocity;

                double dotProduct = incidentX * normalX + incidentY * normalY;

                // Reflection vector calculation
                double reflectionX = incidentX - 2 * dotProduct * normalX;
                double reflectionY = incidentY - 2 * dotProduct * normalY;

                // Normalize the reflection vector and scale it by the original speed
                double reflectionMagnitude = Math.hypot(reflectionX, reflectionY);
                hex.xVelocity = (reflectionX / reflectionMagnitude) * speed;
                hex.yVelocity = (reflectionY / reflectionMagnitude) * speed;

                // Ensure the fireball bounces away from the staff correctly
                hex.yVelocity = -Math.abs(hex.yVelocity);
            }

            if (hex.getY() + hex.getDiameter() > magicalStaff.getY() + magicalStaff.getHeight()) {
                magicalStaff.getHexes().remove(hex);
            }

            for (SimpleBarrier barrier : simpleBarriers) {
                if (barrier.collidesWithFireBall(hex)) {
                    hexes.remove(i);
                    i--; // Adjust index after removal
                    if (overFireBall.isActivated()) {
                        overFireBall.handleCollisionResponse(barrier);
                    } else {
                        updateScore();
                        barrier.destroy();
                        barrier.handleCollisionResponse(hex);
                    }
                    break;
                }
            }

            for (ReinforcedBarrier rbarrier : reinforcedBarriers) {
                if (rbarrier.collidesWithFireBall(hex)) {
                    hexes.remove(i);
                    i--; // Adjust index after removal
                    if (overFireBall.isActivated()) {
                        overFireBall.handleCollisionResponse(rbarrier);
                    } else {
                        if (rbarrier.isDestroyed()) {
                            updateScore();
                        }
                        rbarrier.isDestroyed();
                        rbarrier.handleCollisionResponse(hex);
                    }
                    break;
                }
            }

            for (ExplosiveBarrier ebarrier : explosiveBarriers) {
                if (ebarrier.collidesWithFireBall(hex)) {
                    hexes.remove(i);
                    i--; // Adjust index after removal
                    if (overFireBall.isActivated()) {
                        overFireBall.handleCollisionResponse(ebarrier);
                    } else {
                        updateScore();
                        ebarrier.destroy();
                        ebarrier.handleCollisionResponse(hex);
                    }
                    break;
                }
            }

            for (RewardingBarrier rwbarrier : rewardingBarriers) {
                if (rwbarrier.collidesWithFireBall(hex)) {
                    hexes.remove(i);
                    i--; // Adjust index after removal
                    if (overFireBall.isActivated()) {
                        overFireBall.handleCollisionResponse(rwbarrier);
                    } else {
                        updateScore();
                        rwbarrier.destroy();
                        rwbarrier.handleCollisionResponse(hex);
                    }
                    break;
                }
            }
        }
    }
    private void updateGifts() {
        for (RewardingBarrier rwbarrier : rewardingBarriers) {
            rwbarrier.update();
        }
    }
    private void collectGift(Gift gift) {
        switch (gift.getSpellType()) {
            case FELIX_FELICIS:
                this.felixFelicis.activate();
                hud.updateLives(currentPlayer.getChances());
                break;
            case MAGICAL_STAFF_EXPANSION:
                collectedSpells.add(new MagicalStaffExp(magicalStaff));
                break;
            case HEX:
                collectedSpells.add(new Hex(magicalStaff));
                break;
            case OVERWHELMING_FIRE_BALL:
                collectedSpells.add(new OverwhelmingFireBall(fireball));
                break;
        }
    }
    public void activateSpell(Class<? extends Spell> spellClass) {
        boolean spellFound = false;
        for (Spell spell : collectedSpells) {
            if (spellClass.isInstance(spell)) {
                spell.activate();
                collectedSpells.remove(spell);
                System.out.println(spellClass.getSimpleName() + " activated");
                spellFound = true;
                break;
            }
        }
        if (!spellFound) {
            System.out.println(spellClass.getSimpleName() + " not found in collectedSpells");
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

    public void updateScore(){
        score.updateScore();
        hud.updateScore(score.getScoreValue()); // Update the HUD with the new score
        repaint();
    }


    public void updateLives(){
        fireball.isBallActive = false;
        currentPlayer.decreaseChances();
        magicalStaff.updatePosition(getWidth(), getHeight());
        fireball.updatePosition(magicalStaff.getX() + magicalStaff.getWidth() / 3 + 10,  magicalStaff.getY() -  magicalStaff.getHeight() / 160 - 50);
        timer.stop(); // Stop the game loop
        JOptionPane.showMessageDialog(this, "Lives: " + currentPlayer.getChances(), "Watch out!", JOptionPane.INFORMATION_MESSAGE);
        hud.updateLives(currentPlayer.getChances()); // Update the HUD with the new lives count
        repaint();
        timer.start(); // Continue game loop
        if (currentPlayer.getChances() < 1){
            JOptionPane.showMessageDialog(this, "Game Over", "End", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(collectedSpells);
            fireball.isBallActive = false;
            gameRunning = false;
            timer.stop();
        }
    }

    public boolean isGameRunning() {
        return gameRunning;
    }
}
