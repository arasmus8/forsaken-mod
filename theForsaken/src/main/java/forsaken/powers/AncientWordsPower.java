package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;

public class AncientWordsPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(AncientWordsPower.class.getSimpleName());

    public AncientWordsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        type = PowerType.BUFF;

        loadRegion("artifact");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        sb.append(amount);
        if (amount == 1) {
            sb.append(DESCRIPTIONS[1]);
        } else {
            sb.append(DESCRIPTIONS[2]);
        }
        description = sb.toString();
    }

    @Override
    public AbstractPower makeCopy() {
        return new AncientWordsPower(owner, amount);
    }
}
