package Domain;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList; // Import ArrayList class
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GameView extends JPanel implements ComponentListener, ActionListener {
    private MagicalStaff magicalStaff;
    private FireBall fireball;
    private ArrayList<Barrier> movingBarriers;
    private ArrayList<Barrier> allBarriers;
    private List<SimpleBarrier> simpleBarriers; // ArrayList to hold SimpleBarrier objects
    private List<ReinforcedBarrier> reinforcedBarriers;
    private List<ExplosiveBarrier> explosiveBarriers;
    private List<RewardingBarrier> rewardingBarriers;
    private List<Spell> collectedSpells;
    //private MagicalStaffExp magicalStaffExp;
    private FelixFelicis felixFelicis;
    private OverwhelmingFireBall overwhelmingFireBall;
    private Timer timer;
    private boolean gameRunning = true;
    private boolean gameEnded = false;
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
    private GameInfo gameInfo;
    private BufferedImage winBackgroundImage;
    private BufferedImage looseBackgroundImage;
    private Ymir ymir;
    public GameView(int panelWidth, int panelHeight, GameInfoDAO gameInfoDAO, PlayerAccountDAO playerAccountDAO, GameInfo gameInfo) {
        super();
        // initialize all attributes and establish connections to the images
        this.gameInfoDAO =gameInfoDAO;
        this.playerAccountDAO = playerAccountDAO;
        try {
            background = ImageIO.read(new File("Assets/Images/200Background.png"));
            simpleBarrierImage = ImageIO.read(new File("Assets/Images/200Bluegem.png"));
            firmBarrierImage = ImageIO.read(new File("Assets/Images/200Firm.png"));
            explosiveBarrierImage = ImageIO.read(new File("Assets/Images/200Redgem.png"));
            giftBarrierImage = ImageIO.read(new File("Assets/Images/200Greengem.png"));
            looseBackgroundImage = ImageIO.read(new File("Assets/Images/GameOverBackground.png"));
            winBackgroundImage = ImageIO.read(new File("Assets/Images/WinBackground.png"));
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
        this.magicalStaff = new MagicalStaff(panelWidth, panelHeight - 100); // Position MagicalStaff towards the bottom
        this.fireball = new FireBall(magicalStaff.getX() + magicalStaff.getWidth() / 2 - 8, magicalStaff.getY() - 16, null); // Start Fireball from the top middle
        this.felixFelicis = new FelixFelicis(currentPlayer);
        this.overwhelmingFireBall = new OverwhelmingFireBall(fireball);
        this.hexSpell = new Hex(magicalStaff);
        this.hud = new HUD();
        this.score = new Score();
        this.movingBarriers = new ArrayList<>();
        this.allBarriers = new ArrayList<>();
        this.collectedSpells = new ArrayList<>();
        if (gameInfo != null){
            this.gameInfo = gameInfo;
            this.simpleBarriers = gameInfo.getSimpleBarrierList();
            this.reinforcedBarriers = gameInfo.getReinforcedBarrierList();
            this.rewardingBarriers = gameInfo.getRewardingBarrierList();
            this.explosiveBarriers = gameInfo.getExplosiveBarrierList();
            hud.updateLives(gameInfo.getLives());
            score.setScoreValue(gameInfo.getScore());
            linkSpellsToGame(gameInfo.getSpellsAcquired());
        } else {
            this.simpleBarriers = new ArrayList<>(); // Initialize the ArrayList
            this.reinforcedBarriers = new ArrayList<>();
            this.explosiveBarriers = new ArrayList<>();
            this.rewardingBarriers = new ArrayList<>();
            addComponentListener(this);


            int count = GameLayoutPanel.placedBarriers.size();

            // place all barriers
            for (int i = 0; i < count; i++) {
                Rectangle rectangle = GameLayoutPanel.placedBarriers.get(i);
                Integer type = GameLayoutPanel.placedBarrierTypes.get(i);

                if (type == 1) {
                    simpleBarriers.add(new SimpleBarrier((int) rectangle.getX() / 6 * 8, (int) rectangle.getY() / 6 * 8, (int) rectangle.getWidth() / 6 * 8, (int) rectangle.getHeight() / 6 * 8));
                } else if (type == 2) {
                    reinforcedBarriers.add(new ReinforcedBarrier((int) rectangle.getX() / 6 * 8, (int) rectangle.getY() / 6 * 8, (int) rectangle.getWidth() / 6 * 8, (int) rectangle.getHeight() / 6 * 8));
                } else if (type == 3) {
                    explosiveBarriers.add(new ExplosiveBarrier((int) rectangle.getX() / 6 * 8, (int) rectangle.getY() / 6 * 8, (int) rectangle.getWidth() / 6 * 8, (int) rectangle.getHeight() / 6 * 8));
                } else {
                    rewardingBarriers.add(new RewardingBarrier((int) rectangle.getX() / 6 * 8, (int) rectangle.getY() / 6 * 8, (int) rectangle.getWidth() / 6 * 8, (int) rectangle.getHeight() / 6 * 8));
                }
            }
        }

        for (SimpleBarrier simple : simpleBarriers){
            if(simple.moves){
                movingBarriers.add(simple);
            }
            allBarriers.add(simple);
        }
        for (ReinforcedBarrier reinf : reinforcedBarriers){
            if(reinf.moves){
                movingBarriers.add(reinf);
            }
            allBarriers.add(reinf);
        }
        for (ExplosiveBarrier explo : explosiveBarriers){
            if(explo.moves){
                movingBarriers.add(explo);
            }
            allBarriers.add(explo);
        }
        for (RewardingBarrier rewa : rewardingBarriers){
            allBarriers.add(rewa);
        }

        timer = new Timer(10, this);
        timer.start();
        this.ymir = new Ymir(this);
    }
    void saveGameInfo() {
        GameInfo gameInfo;
        if (this.gameInfo != null){
            gameInfo = this.gameInfo;
            gameInfo.setPlayerId(gameInfo.getPlayerId());
            gameInfo.setScore((int) score.getScoreValue());
            gameInfo.setLives(currentPlayer.getChances());
            gameInfo.setGameState(GameState.PASSIVE);
            gameInfo.setLastSaved(new Date());
            gameInfo.setSpellsAcquired(collectedSpells);
            gameInfo.setSimpleBarrierList(simpleBarriers);
            gameInfo.setReinforcedBarrierList(reinforcedBarriers);
            gameInfo.setRewardingBarrierList(rewardingBarriers);
            gameInfo.setExplosiveBarrierList(explosiveBarriers);
        } else {
            gameInfo = new GameInfo();
            PlayerAccount playerAccount = playerAccountDAO.findPlayerAccountByUsername(currentPlayer.getUsername());
            gameInfo.setPlayerId(playerAccount.getPlayerId());
            gameInfo.setScore((int) score.getScoreValue());
            gameInfo.setLives(currentPlayer.getChances());
            gameInfo.setGameState(GameState.PASSIVE);
            gameInfo.setLastSaved(new Date());
            gameInfo.setSpellsAcquired(collectedSpells);
            gameInfo.setSimpleBarrierList(simpleBarriers);
            gameInfo.setReinforcedBarrierList(reinforcedBarriers);
            gameInfo.setRewardingBarrierList(rewardingBarriers);
            gameInfo.setExplosiveBarrierList(explosiveBarriers);
        }
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
    // paint all components
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameRunning) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            magicalStaff.draw(g);

            fireball.draw(g);
            // Draw all SimpleBarrier objects in the ArrayList
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
            for (FireBall hex : magicalStaff.getHexes()) {
                hex.draw(g);
            }
        } else if (gameEnded) {
            // Draw the end game background based on win or lose
            if (currentPlayer.getChances() <= 0) {
                g.drawImage(looseBackgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else if (allBarriers.size() < 1) {
                g.drawImage(winBackgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
            remove(hud.getLivesLabel());
            remove(hud.getScoreLabel());
            currentPlayer.setChances(3);
        }
    }
    private void linkSpellsToGame(List<Spell> rawSpells) {
        for (Spell rawSpell : rawSpells) {
            for (long i = 0; i < rawSpell.getCount(); i++) {
                switch (rawSpell.getSpellType()) {
                    case HEX:
                        collectedSpells.add(new Hex(magicalStaff));
                        break;
                    case OVERWHELMING_FIRE_BALL:
                        collectedSpells.add(new OverwhelmingFireBall(fireball));
                        break;
                    case MAGICAL_STAFF_EXPANSION:
                        collectedSpells.add(new MagicalStaffExp(magicalStaff));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown spell type: " + rawSpell.getSpellType());
                }
            }
        }
    }
    // game loop
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            if (fireball.isBallActive) {
                fireball.move(getWidth(), getHeight());

                for (Barrier each : movingBarriers){
                    each.move(getWidth(),getHeight(),allBarriers);
                }

                checkCollisions();
                updateGifts();
            }
            magicalStaff.moveHexes(getWidth(), getHeight()); // Update hex positions
            repaint();
        }
    }

    private void checkCollisions() {

        // checks correct FireBall bouncing off borders
        if (fireball.getX() <= 0 || fireball.getX() + fireball.getDiameter() >= getWidth()) {
            fireball.reverseXDirection();
        }

        if (fireball.getY() <= 0) {
            fireball.reverseYDirection();
        }


        // checks correct FireBall bouncing off staff
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

        // check if Barriers hit by Hex
        ArrayList<FireBall> hexes = magicalStaff.getHexes();
        ArrayList<FireBall> hexesToRemove = new ArrayList<>();
        ArrayList<Barrier> allToRemove = new ArrayList<>();
        ArrayList<Barrier> movingToRemove = new ArrayList<>();
        for (FireBall hex : hexes) {
            for (SimpleBarrier barrier : simpleBarriers) {
                if (barrier.collidesWithFireBall(hex) && !barrier.isFrozen ) {
                    if (hex.isOverwhelming()) {
                        updateScore();
                        barrier.destroy();
                        hexesToRemove.add(hex);
                        if(barrier.moves){
                            movingToRemove.add(barrier);
                        }
                        allToRemove.add(barrier);
                    } else {
                        updateScore();
                        barrier.destroy();
                        barrier.handleCollisionResponse(hex);
                        hexesToRemove.add(hex);
                        if(barrier.moves){
                            movingToRemove.add(barrier);
                        }
                        allToRemove.add(barrier);
                    }
                    allToRemove.add(barrier);
                }
            }
            for (ReinforcedBarrier rbarrier : reinforcedBarriers) {
                if (rbarrier.collidesWithFireBall(hex) && !rbarrier.isFrozen) {
                    if (hex.isOverwhelming()) {
                        updateScore();
                        rbarrier.destroy();
                        hexesToRemove.add(hex);
                        if(rbarrier.moves){
                            movingToRemove.add(rbarrier);
                        }
                        allToRemove.add(rbarrier);
                    } else {
                        if (rbarrier.isDestroyed()) {
                            updateScore();
                            if(rbarrier.moves){
                                movingToRemove.add(rbarrier);
                            }
                            allToRemove.add(rbarrier);
                        }
                        rbarrier.isDestroyed();
                        rbarrier.handleCollisionResponse(hex);
                        hexesToRemove.add(hex);
                        allToRemove.add(rbarrier);
                    }
                    allToRemove.add(rbarrier);
                }
            }
            for (ExplosiveBarrier ebarrier : explosiveBarriers) {
                if (ebarrier.collidesWithFireBall(hex)&& !ebarrier.isFrozen) {
                    if (hex.isOverwhelming()) {
                        updateScore();
                        ebarrier.destroy();
                        hexesToRemove.add(hex);
                        if(ebarrier.moves){
                            movingToRemove.add(ebarrier);
                        }
                        allToRemove.add(ebarrier);
                    } else {
                        updateScore();
                        ebarrier.destroy();
                        if(ebarrier.moves){
                            movingToRemove.add(ebarrier);
                        }
                        allToRemove.add(ebarrier);
                    }
                    ebarrier.handleCollisionResponse(hex);
                    hexesToRemove.add(hex);
                    allToRemove.add(ebarrier);
                }
                if (ebarrier.destroyed ) {
                    if (ebarrier.collidesWithMagicalStaff(magicalStaff)) {
                        updateLives();
                        ebarrier.staffHit();
                        allToRemove.add(ebarrier);
                    }
                }
            }
            for (RewardingBarrier rwbarrier : rewardingBarriers) {
                if (rwbarrier.collidesWithFireBall(hex)&& !rwbarrier.isFrozen) {
                    if (hex.isOverwhelming()) {
                        if(!rwbarrier.destroyed){
                            updateScore();
                        };
                        rwbarrier.destroy();
                        hexesToRemove.add(hex);
                        allToRemove.add(rwbarrier);
                    } else {
                        if(!rwbarrier.destroyed){
                            updateScore();
                        };
                        rwbarrier.destroy();
                        rwbarrier.handleCollisionResponse(hex);
                        hexesToRemove.add(hex);
                        allToRemove.add(rwbarrier);
                    }
                    allToRemove.add(rwbarrier);
                }
                if (rwbarrier.getGift() != null && rwbarrier.collidesWithMagicalStaff(magicalStaff)) {
                    collectGift(rwbarrier.getGift());
                    System.out.println("Gift taken: " + rwbarrier.getGift().getSpellType());
                    rwbarrier.setCollected(true);
                    allToRemove.add(rwbarrier);
                    //break;
                }
            }
        }

        hexes.removeAll(hexesToRemove);

        // check if Barriers hit by Fireball or OverwhelmingFireBall
        for (SimpleBarrier barrier : simpleBarriers) {
            if (barrier.collidesWithFireBall(fireball)) {
                if (fireball.isOverwhelming() ) {
                    if(barrier.IsAddScore){
                        updateScore();
                    }
                    barrier.destroy();
                    if(barrier.moves){
                        movingToRemove.add(barrier);
                    }
                    allToRemove.add(barrier);
                } else {
                    if(!barrier.isFrozen)
                    {
                    if(barrier.IsAddScore){
                        updateScore();}
                        barrier.destroy();
                        barrier.handleCollisionResponse(fireball);
                        if(barrier.moves){
                            movingToRemove.add(barrier);
                        }
                        allToRemove.add(barrier);
                    }
                    else{

                        barrier.handleCollisionResponse(fireball);
                    }


                }
                allToRemove.add(barrier);
            }
        }

        for (ReinforcedBarrier rbarrier : reinforcedBarriers) {
            if (rbarrier.collidesWithFireBall(fireball)) {
                if (fireball.isOverwhelming()) {
                    if(!rbarrier.isFrozen){
                    updateScore();
                    rbarrier.destroy();
                    if(rbarrier.moves){
                        movingToRemove.add(rbarrier);
                    }
                    allToRemove.add(rbarrier);
                    }
                    else{
                        if (rbarrier.isDestroyed()) {
                            updateScore();
                            if(rbarrier.moves){
                                movingToRemove.add(rbarrier);
                            }
                            allToRemove.add(rbarrier);
                        }
                        rbarrier.handleCollisionResponse(fireball);

                    }
                } else {
                    if(!rbarrier.isFrozen){
                    if (rbarrier.isDestroyed()) {
                        updateScore();
                        if(rbarrier.moves){
                            movingToRemove.add(rbarrier);
                        }
                        allToRemove.add(rbarrier);
                    }
                    rbarrier.handleCollisionResponse(fireball);
                }
                    else{fireball.reverseYDirection();}
                }
                allToRemove.add(rbarrier);
            }
        }

        for (ExplosiveBarrier ebarrier : explosiveBarriers) {
            if (ebarrier.collidesWithFireBall(fireball)) {
                if (fireball.isOverwhelming() ) {
                    updateScore();
                    ebarrier.destroy();
                    if(ebarrier.moves){
                        movingToRemove.add(ebarrier);
                    }
                    allToRemove.add(ebarrier);
                } else {
                    if(!ebarrier.isFrozen){
                        updateScore();
                        ebarrier.destroy();
                        if(ebarrier.moves){
                            movingToRemove.add(ebarrier);
                        }
                        allToRemove.add(ebarrier);
                    ebarrier.handleCollisionResponse(fireball);}
                    else{
                        fireball.reverseYDirection();
                    }
                }
                ebarrier.handleCollisionResponse(fireball);
                allToRemove.add(ebarrier);
            }
            if(ebarrier.destroyed) {
                if (ebarrier.collidesWithMagicalStaff(magicalStaff)) {
                    updateLives();
                    ebarrier.staffHit();
                    allToRemove.add(ebarrier);
                }
            }
        }

        for (RewardingBarrier rwbarrier : rewardingBarriers) {
            if (rwbarrier.collidesWithFireBall(fireball)) {
                if (fireball.isOverwhelming()) {
                    if(!rwbarrier.destroyed){
                        updateScore();
                    }
                    rwbarrier.destroy();
                    allToRemove.add(rwbarrier);
                } else {
                    if(!rwbarrier.isFrozen) {
                        if(!rwbarrier.destroyed){
                            updateScore();
                        }
                        rwbarrier.destroy();
                        rwbarrier.handleCollisionResponse(fireball);
                        allToRemove.add(rwbarrier);
                    }
                    else{
                        fireball.reverseYDirection();
                    }
                }
            }
            if (rwbarrier.getGift() != null && rwbarrier.collidesWithMagicalStaff(magicalStaff)) {
                collectGift(rwbarrier.getGift());
                System.out.println("Gift taken: " + rwbarrier.getGift().getSpellType());
                rwbarrier.setCollected(true);
                allToRemove.add(rwbarrier);
            }
        }

        movingBarriers.removeAll(movingToRemove);
        allBarriers.removeAll(allToRemove);
        System.out.println("movingBarriers size is: "+ movingBarriers.size());
        System.out.println("allBarriers size is: "+ allBarriers.size());
        if (allBarriers.size() < 1){
            magicalStaff.stopFiring();
            JOptionPane.showMessageDialog(this, "You have received the lance of destiny! All power is yours! Click Pause to quit or go back to Menu.", "End", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(collectedSpells);
            fireball.isBallActive = false;
            gameRunning = false;
            gameEnded = true;
            timer.stop();
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
        fireball.updatePosition(magicalStaff.getX() + magicalStaff.getWidth() / 2 - 8, magicalStaff.getY() - 16);
        timer.stop(); // Stop the game loop
        if (currentPlayer.getChances() > 0) JOptionPane.showMessageDialog(this, "Lives: " + currentPlayer.getChances(), "Watch out!", JOptionPane.INFORMATION_MESSAGE);
        hud.updateLives(currentPlayer.getChances()); // Update the HUD with the new lives count
        repaint();
        timer.start(); // Continue game loop
        if (currentPlayer.getChances() < 1){
            magicalStaff.stopFiring();
            JOptionPane.showMessageDialog(this, "You have been defeated. You are unworthy of the lance of destiny. Click Pause to go back or quit.", "End", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(collectedSpells);
            fireball.isBallActive = false;
            gameRunning = false;
            gameEnded = true;
            timer.stop();
        }
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public List<Barrier> getAllBarriers() {
        return allBarriers;
    }

    // Getter method for fireball
    public FireBall getFireball() {
        return fireball;
    }

    // Method to add a Hollow Purple Barrier
    public void addHollowPurpleBarrier(HollowPurpleBarrier barrier) {
        allBarriers.add(barrier);
        simpleBarriers.add(barrier);
        repaint();
    }

    public FireBall getFireBall(){
        return this.fireball;
    }

    public Rectangle getMagicalStaffBounds() {
        return new Rectangle((int) magicalStaff.getX(), (int) magicalStaff.getY(), (int) magicalStaff.getWidth(), (int) magicalStaff.getHeight());
    }

}
