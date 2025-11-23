package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;


//Double damage next turn when block is broken.

public class PrudencePower extends AbstractForsakenPower implements CloneablePowerInterface {
    private static final String POWER_ID = TheForsakenMod.makeID(PrudencePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PrudencePower(int amount) {
        super(POWER_ID, AbstractDungeon.player, amount);

        type = PowerType.BUFF;

        loadRegion("prudence");
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer p = AbstractDungeon.player;
            int cardCount = p.hand.size();
            int blockAmount = cardCount * amount;
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, blockAmount));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PrudencePower(amount);
    }
}
