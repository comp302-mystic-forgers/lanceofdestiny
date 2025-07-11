package Domain;
import java.awt.*;
import javax.swing.JLabel;


public class HUD {
    private JLabel livesLabel;
    private JLabel scoreLabel;

    public HUD() {

        livesLabel = new JLabel("Lives: 3");
        livesLabel.setForeground(Color.RED);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        //livesLabel.setHorizontalAlignment(JLabel.WEST);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        //scoreLabel.setHorizontalAlignment(JLabel.EAST);
    }

    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }

    public void updateScore(long score) {
        scoreLabel.setText("Score: " + score);
    }


    public JLabel getLivesLabel() {
        return livesLabel;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }
}
