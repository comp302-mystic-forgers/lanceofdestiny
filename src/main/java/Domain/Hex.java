package Domain;

public class Hex extends Spell {

    private MagicalStaff staff;

    public Hex(MagicalStaff staff) {
        this.staff = staff;
        this.staff.equipCanons(); // Attaching two magical canons to the staff
    }

    @Override
    public void activate() {
        System.out.println("Hex activated: Magical canons are firing");
        staff.startFiring();
    }

    @Override
    public void deactivate() {
        System.out.println("Hex deactivated: Magical canons stopped firing");
        staff.stopFiring();
    }

    public MagicalStaff getStaff() {
        return staff;
    }

    public void setStaff(MagicalStaff staff) {
        this.staff = staff;
    }
}
