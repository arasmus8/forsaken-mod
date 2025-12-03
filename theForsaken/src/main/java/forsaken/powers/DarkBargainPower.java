package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.cards.Recompense;

public class DarkBargainPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(DarkBargainPower.class.getSimpleName());

    public DarkBargainPower(AbstractCreature owner) {
        super(POWER_ID, owner, 1);
        type = PowerType.BUFF;

        loadRegion("dark_bargain");
        updateDescription();
    }

    private DarkBargainPower(AbstractCreature owner, int amount) {
        this(owner);
        this.amount = amount;
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        qAction(new GainEnergyAction(amount));
        makeInDiscard(new Recompense());
    }

    @Override
    public void updateDescription() {
        StringBuilder builder = new StringBuilder();
        builder.append(DESCRIPTIONS[0]);
        for (int i = 0; i < amount; i++) {
            builder.append("[E] ");
        }
        builder.append(DESCRIPTIONS[1]);
        description = builder.toString();
    }

    @Override
    public AbstractPower makeCopy() {
        return new DarkBargainPower(owner, amount);
    }
}