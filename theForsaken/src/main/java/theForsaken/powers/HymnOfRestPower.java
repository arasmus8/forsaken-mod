package theForsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theForsaken.TheForsakenMod;


//Double damage next turn when block is broken.

public class HymnOfRestPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(HymnOfRestPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean retainAll = false;

    public HymnOfRestPower(final AbstractCreature owner, final int amount, final boolean retainAll) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.retainAll = retainAll;

        type = PowerType.BUFF;

        loadRegion("hymn_of_rest");
        updateDescription();
    }

    public void retainAll() {
        this.retainAll = true;
    }

    public int modifiedBlock(int removing) {
        if (retainAll) {
            return 0;
        }
        double r = removing;
        return (int) Math.ceil(r / 2.0);
    }

    @Override
    public void atEndOfRound() {
        if (amount > 1) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, POWER_ID, 1));
        } else {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    /*
    "DESCRIPTIONS": [
      "Retain ",
      "half ",
      "your block ",
      "this turn.",
      "for #b",
      "turns."
    ]
     */
    @Override
    public void updateDescription() {
        String rest = amount > 1 ? DESCRIPTIONS[4] + amount + ' ' + DESCRIPTIONS[5] : DESCRIPTIONS[3];
        if (retainAll) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + rest;
        } else {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2] + rest;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new HymnOfRestPower(owner, amount, retainAll);
    }
}
