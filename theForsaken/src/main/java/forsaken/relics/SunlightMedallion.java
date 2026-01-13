package forsaken.relics;

import forsaken.TheForsakenMod;
import forsaken.powers.SunlightPower;

public class SunlightMedallion extends AbstractForsakenRelic {
    public static final String ID = TheForsakenMod.makeID(SunlightMedallion.class.getSimpleName());
    public SunlightMedallion() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        flash();
        applyToSelf(new SunlightPower(3));
    }
}
