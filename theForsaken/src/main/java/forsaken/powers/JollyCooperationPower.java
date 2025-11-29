package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;

public class JollyCooperationPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(JollyCooperationPower.class.getSimpleName());

    public JollyCooperationPower() {
        super(POWER_ID, AbstractDungeon.player, -1);
        type = PowerType.BUFF;

        loadRegion("energized_green");
        updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return null;
    }
}
