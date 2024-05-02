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
        if (!activated) {
            activated = true;
            activationTime = System.currentTimeMillis();
            staff.setHeight(staff.getHeight() * 2); // Double the length of the staff
        }
    }

    @Override
    public void deactivate() {
        if (activated) {
            activated = false;
            staff.setHeight(staff.getHeight() / 2); // Return to original length
        }
    }

    public boolean isActivated() {
        return activated;
    }

}