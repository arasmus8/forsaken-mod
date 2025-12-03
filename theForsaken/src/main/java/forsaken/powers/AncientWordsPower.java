package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;

public class AncientWordsPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(AncientWordsPower.class.getSimpleName());

    public AncientWordsPower(AbstractCreature owner) {
        super(POWER_ID, owner, -1);
        type = PowerType.BUFF;

        loadRegion("artifact");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AncientWordsPower(owner);
    }
}
