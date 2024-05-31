package Domain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverwhelmingFireBall extends Spell{
    private FireBall fireBall;
    private Timer timer;
    public OverwhelmingFireBall(FireBall fireBall) {
        super(SpellType.OVERWHELMING_FIRE_BALL, "Upgrades the Fire Ball to destroy any barrier for 30 seconds.", 1);
        this.fireBall = fireBall;
    }

    @Override
    public void activate() {
        if (!isActivated()) {
            setActivated(true);
            fireBall.setOverwhelming(true);
            timer = new Timer(30000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deactivate();
                }
            });
        }
    }

    public void deactivate() {
        fireBall.setOverwhelming(false);
        setActivated(false);
    }

}

