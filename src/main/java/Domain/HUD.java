package Domain;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HUD extends JPanel {
    private JLabel livesLabel;
    private JLabel scoreLabel;

    public HUD() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 100));

        JPanel leftPanel = new JPanel();
        livesLabel = new JLabel("Lives: 3");
        livesLabel.setForeground(Color.GREEN);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(livesLabel);

        JPanel rightPanel = new JPanel();
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(scoreLabel);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
    }

    public void updateScore(long score) {
        scoreLabel.setText("Score: " + score);
    }
}
