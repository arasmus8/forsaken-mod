package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.oldCards.Recompense;

public class DarkBargainPower extends AbstractForsakenPower implements CloneablePowerInterface {
    private static final String POWER_ID = TheForsakenMod.makeID(DarkBargainPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DarkBargainPower(final AbstractCreature owner, final int amount) {
        super(POWER_ID, owner, amount);

        type = PowerType.BUFF;
        isTurnBased = false;

        loadRegion("dark_bargain");
        updateDescription();
    }

    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);

        for(int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }

        if(amount > 1) {
            sb.append(DESCRIPTIONS[1]);
            sb.append(this.amount);
            sb.append(DESCRIPTIONS[3]);
        } else {
            sb.append(DESCRIPTIONS[1]);
            sb.append(this.amount);
            sb.append(DESCRIPTIONS[2]);
        }

        this.description = sb.toString();
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Recompense(), this.amount));
        this.flash();
    }

    @Override
    public AbstractPower makeCopy() {
        return new DarkBargainPower(owner, amount);
    }
}
