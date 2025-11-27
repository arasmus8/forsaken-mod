package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;


public class FountainOfLifePower extends AbstractForsakenPower implements CloneablePowerInterface {
    private static final String POWER_ID = TheForsakenMod.makeID(FountainOfLifePower.class.getSimpleName());

    public FountainOfLifePower(final AbstractCreature owner, final int amount) {
        super(POWER_ID, owner, amount);

        type = PowerType.BUFF;
        isTurnBased = false;

        loadRegion("fountain_of_life");
        updateDescription();
    }

    @Override
    public void onVictory() {
        AbstractDungeon.player.increaseMaxHp(amount, false);
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new FountainOfLifePower(owner, amount);
    }
}
