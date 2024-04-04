package Domain;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameView extends JFrame {

    private DrawingPanel drawingPanel;

    public GameView() {
        super("Lance of Destiny");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingPanel = new DrawingPanel();
        this.add(drawingPanel);

        // Set up key bindings
        this.setFocusable(true); // To ensure the JFrame can catch key events
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                drawingPanel.moveStaff(e.getKeyCode());
            }
        });

        this.setVisible(true);
    }

    // Drawing panel inner class
    class DrawingPanel extends JPanel {
        private int staffXPosition = this.getWidth() / 2; // Initial X position

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Update staff position based on panel width
            int panelWidth = this.getWidth();
            int staffWidth = (int) (panelWidth * 0.1); // 10% of the screen width
            int staffHeight = 20; // The thickness of the Magical Staff
            int yPosition = this.getHeight() - staffHeight - 30; // Position from the bottom

            // Draw the Magical Staff
            g.setColor(Color.BLACK);
            g.fillRect(staffXPosition, yPosition, staffWidth, staffHeight);
        }

        public void moveStaff(int keyCode) {
            int movementSpeed = 10; // Change this to adjust the speed
            int panelWidth = this.getWidth();
            int staffWidth = (int) (panelWidth * 0.1);

            // Move left
            if (keyCode == KeyEvent.VK_LEFT) {
                staffXPosition -= movementSpeed;
                if (staffXPosition < 0) { // Prevent going beyond the panel bounds
                    staffXPosition = 0;
                }
            }
            // Move right
            else if (keyCode == KeyEvent.VK_RIGHT) {
                staffXPosition += movementSpeed;
                if (staffXPosition + staffWidth > panelWidth) { // Prevent going beyond the panel bounds
                    staffXPosition = panelWidth - staffWidth;
                }
            }

            repaint(); // Redraw the panel with the updated position
        }
    }

    public static void main(String[] args) {
        new GameView();
    }
}

