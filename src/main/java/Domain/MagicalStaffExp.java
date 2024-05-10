package Domain;

import java.awt.*;

public class MagicalStaffExp extends Spell{
    private MagicalStaff staff;
    private boolean activated;
    private long activationTime;

    public MagicalStaffExp(MagicalStaff staff) {
        this.staff = staff;
    }

    public long getTime(){
        return activationTime;
    }

    @Override
    public void activate(){
        if (!activated && staff.counterPaint < 1) {
            activated = true;
            activationTime = System.currentTimeMillis();
            staff.setActivatedHeight();
        }
    }

    @Override
    public void deactivate() {
        if (activated && staff.counterRepaint < 1) {
            activated = false;
            staff.setDeactivatedHeight();
        }
    }

    public boolean isActivated() {
        return activated;
    }

}