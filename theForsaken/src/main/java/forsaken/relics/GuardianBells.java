package forsaken.relics;

import forsaken.TheForsakenMod;

public class GuardianBells extends AbstractForsakenRelic {
    public static final String ID = TheForsakenMod.makeID(GuardianBells.class.getSimpleName());
    public GuardianBells() {
        super(ID, RelicTier.RARE, LandingSound.HEAVY);
    }
}
