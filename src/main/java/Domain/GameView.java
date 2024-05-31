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
        if (gameInfo != null){
            this.gameInfo = gameInfo;
            this.simpleBarriers = gameInfo.getSimpleBarrierList();
            this.reinforcedBarriers = gameInfo.getReinforcedBarrierList();
            this.rewardingBarriers = gameInfo.getRewardingBarrierList();
            this.explosiveBarriers = gameInfo.getExplosiveBarrierList();
            System.out.println("spells: " + gameInfo.getSpellsAcquired());
            this.collectedSpells = gameInfo.getSpellsAcquired();
            System.out.println("collected spells: " + collectedSpells);
            hud.updateLives(gameInfo.getLives());
            score.setScoreValue(gameInfo.getScore());
        } else {
            this.simpleBarriers = new ArrayList<>(); // Initialize the ArrayList
            this.reinforcedBarriers = new ArrayList<>();
            this.explosiveBarriers = new ArrayList<>();
            this.rewardingBarriers = new ArrayList<>();
            this.collectedSpells = new ArrayList<>();
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
                    }
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
                }
                if (ebarrier.destroyed ) {
                    if (ebarrier.collidesWithMagicalStaff(magicalStaff)) {
                        updateLives();
                        ebarrier.staffHit();
                    }
                }
            }
            for (RewardingBarrier rwbarrier : rewardingBarriers) {
                if (rwbarrier.collidesWithFireBall(hex)&& !rwbarrier.isFrozen) {
                    if (hex.isOverwhelming()) {
                        updateScore();
                        rwbarrier.destroy();
                        hexesToRemove.add(hex);
                        if(rwbarrier.moves){
                            movingToRemove.add(rwbarrier);
                        }
                        allToRemove.add(rwbarrier);
                    } else {
                        updateScore();
                        rwbarrier.destroy();
                        rwbarrier.handleCollisionResponse(hex);
                        hexesToRemove.add(hex);
                        if(rwbarrier.moves){
                            movingToRemove.add(rwbarrier);
                        }
                        allToRemove.add(rwbarrier);
                    }
                }
                if (rwbarrier.getGift() != null && rwbarrier.collidesWithMagicalStaff(magicalStaff)) {
                    collectGift(rwbarrier.getGift());
                    System.out.println("Gift taken: " + rwbarrier.getGift().getSpellType());
                    rwbarrier.setCollected(true);
                    break;
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
            }
            if(ebarrier.destroyed) {
                if (ebarrier.collidesWithMagicalStaff(magicalStaff)) {
                    updateLives();
                    ebarrier.staffHit();
                }
            }
        }

        for (RewardingBarrier rwbarrier : rewardingBarriers) {
            if (rwbarrier.collidesWithFireBall(fireball)) {
                if (fireball.isOverwhelming()) {
                    updateScore();
                    rwbarrier.destroy();
                    if(rwbarrier.moves){
                        movingToRemove.add(rwbarrier);
                    }
                    allToRemove.add(rwbarrier);
                } else {
                    if(!rwbarrier.isFrozen) {
                        updateScore();

                        rwbarrier.destroy();


                        rwbarrier.handleCollisionResponse(fireball);
                        if (rwbarrier.moves) {
                            movingToRemove.add(rwbarrier);
                        }
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
            }
        }

        movingBarriers.removeAll(movingToRemove);
        allBarriers.removeAll(allToRemove);
        System.out.println("movingBarriers size is: "+ movingBarriers.size());
        System.out.println("allBarriers size is: "+ allBarriers.size());
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
        JOptionPane.showMessageDialog(this, "Lives: " + currentPlayer.getChances(), "Watch out!", JOptionPane.INFORMATION_MESSAGE);
        hud.updateLives(currentPlayer.getChances()); // Update the HUD with the new lives count
        repaint();
        timer.start(); // Continue game loop
        if (currentPlayer.getChances() < 1){
            magicalStaff.stopFiring();
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
