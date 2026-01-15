package forsaken.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

public class TearstoneRing extends AbstractForsakenRelic{
    public static final String ID = TheForsakenMod.makeID(TearstoneRing.class.getSimpleName());
    private static final int VIGOR_AMOUNT = 5;

    public  TearstoneRing() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
        counter = 0;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (AbstractForsakenCard.isUnplayable(drawnCard)) {
            counter += 1;
        }
        if (counter >= 10) {
            flash();
            counter = 0;
            applyToSelf(new VigorPower(AbstractDungeon.player, VIGOR_AMOUNT));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + VIGOR_AMOUNT + DESCRIPTIONS[1];
    }
}
