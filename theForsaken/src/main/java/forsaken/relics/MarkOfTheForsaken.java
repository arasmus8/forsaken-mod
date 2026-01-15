package forsaken.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cardmods.BonusDamageMod;

public class MarkOfTheForsaken extends AbstractForsakenRelic{
    public static final String ID = TheForsakenMod.makeID(MarkOfTheForsaken.class.getSimpleName());

    public MarkOfTheForsaken(){
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStartPostDraw() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(WeakPower.POWER_ID) || p.hasPower(VulnerablePower.POWER_ID)) {
            addToBot(new FunctionalAction(firstUpdate -> {
                flash();
                p.hand.group.stream()
                        .filter(c -> c.type.equals(AbstractCard.CardType.ATTACK))
                        .forEach(c -> BonusDamageMod.applyToCard(c, 1));
                return true;
            }));
        }
    }
}
