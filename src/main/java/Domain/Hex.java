package Domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Hex extends Spell {

    private MagicalStaff staff;
    private Timer hexTimer;

    public Hex(MagicalStaff staff) {
        super(SpellType.HEX, "Equips the Magical Staff with two magical cannons for 30 seconds.");
        this.staff = staff;
        this.staff.equipCanons();// Attaching two magical canons to the staff
        startTimer();
    }


    private void startTimer(){
        hexTimer = new Timer(30000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                deactivate();
            }
        });
        hexTimer.setRepeats(false);
    }

    @Override
    public void activate() {
        System.out.println("Hex activated: Magical canons are firing");
        staff.startFiring();
        hexTimer.start();
    }

    public void deactivate() {
        System.out.println("Hex deactivated: Magical canons stopped firing");
        staff.stopFiring();
        hexTimer.stop();
    }

    public MagicalStaff getStaff() {
        return staff;
    }

    public void setStaff(MagicalStaff staff) {
        this.staff = staff;
    }
}
