package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;

public class TearsOfSunlightPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(TearsOfSunlightPower.class.getSimpleName());

    public TearsOfSunlightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        type = PowerType.BUFF;

        loadRegion("tears_of_sunlight");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TearsOfSunlightPower(owner, amount);
    }
}
