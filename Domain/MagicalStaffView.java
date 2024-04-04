package Domain;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
public class MagicalStaffView extends JPanel implements ComponentListener {
    private MagicalStaff magicalStaff;

    public MagicalStaffView(int panelWidth, int panelHeight) {
        super();
        this.magicalStaff = new MagicalStaff(panelWidth, panelHeight);
        addComponentListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        magicalStaff.draw(g); // Draw the magical staff with its current rotation
    }

    public void moveStaff(int keyCode) {
        magicalStaff.move(keyCode, getWidth());
        repaint(); // Redraw the panel with the updated position
    }

    public void rotateStaff(int keyCode) {
        magicalStaff.rotate(keyCode);
        repaint(); // Redraw the panel to reflect the rotation
    }
    @Override
    public void componentResized(ComponentEvent e) {
        magicalStaff.updatePosition(getWidth(), getHeight());
        repaint(); // Redraw the panel with the updated staff position
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
}


