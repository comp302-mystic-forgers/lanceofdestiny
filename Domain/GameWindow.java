package Domain;
import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {
    private MagicalStaffView magicalStaffView;

    public GameWindow() {
        super("Lance of Destiny");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        magicalStaffView = new MagicalStaffView(getWidth(), getHeight());
        add(magicalStaffView);

        // Key bindings for moving and rotating the staff
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    magicalStaffView.moveStaff(e.getKeyCode());
                } else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
                    magicalStaffView.rotateStaff(e.getKeyCode());
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new GameWindow();
    }
}

