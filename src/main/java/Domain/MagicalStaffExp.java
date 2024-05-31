package Domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MagicalStaffExp extends Spell{
    private MagicalStaff staff;
    private boolean activated;
    private double originalWidth;
    private Timer timer;

    public MagicalStaffExp(MagicalStaff magicalStaff) {
        super(SpellType.MAGICAL_STAFF_EXPANSION, "Doubles the length of the Magical Staff for 30 seconds.", 1);
        this.staff = magicalStaff;
        this.activated = false;
        this.originalWidth = magicalStaff.getWidth();
    }

    @Override
    public void activate() {
        if (!activated) {
            staff.setWidth(originalWidth * 2);
            staff.setActivatedHeight(); // Ensure the staff is repainted after changing width
            System.out.println("Magical Staff Expansion activated");

            timer = new Timer(30000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deactivate();
                }
            });
            timer.setRepeats(false);
            timer.start();
            activated = true;
        }
    }

    public void deactivate() {
        staff.setWidth(originalWidth);
        staff.setDeactivatedHeight(); // Ensure the staff is repainted after reverting width
        System.out.println("Magical Staff Expansion deactivated");
        activated = false;
    }

    public boolean isActivated() {
        return activated;
    }

}