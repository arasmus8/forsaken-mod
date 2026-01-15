package forsaken.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;

public class WornEffigy extends AbstractForsakenRelic {
    public static final String ID = TheForsakenMod.makeID(WornEffigy.class.getSimpleName());
    private static final int DRAW_AMOUNT = 2;

    public WornEffigy() {
        super(ID, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onTrigger() {
        addToBot(new FunctionalAction(firstUpdate -> {
            addToBot(new DrawCardAction(DRAW_AMOUNT));
            return true;
        }));
    }
}
