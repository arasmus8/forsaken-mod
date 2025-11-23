package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;

public class SunlightPower extends AbstractForsakenPower implements CloneablePowerInterface {
    private static final int HEAL_AMOUNT = 3;

    public SunlightPower(int amount) {
        super(TheForsakenMod.makeID(SunlightPower.class.getSimpleName()), AbstractDungeon.player, amount);
        type = PowerType.BUFF;
        isTurnBased = true;

        loadRegion("regen");
        updateDescription();
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        qAction(new HealAction(owner, owner, HEAL_AMOUNT));
        qAction(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SunlightPower(amount);
    }
}
