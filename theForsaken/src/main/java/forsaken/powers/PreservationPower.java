package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import forsaken.TheForsakenMod;


public class PreservationPower extends AbstractForsakenPower implements CloneablePowerInterface {
    private static final String POWER_ID = TheForsakenMod.makeID(PreservationPower.class.getSimpleName());

    public PreservationPower(final AbstractCreature owner) {
        super(POWER_ID, owner, -1);

        type = PowerType.BUFF;

        loadRegion("preservation");
        updateDescription();
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        int block = MathUtils.floor(blockAmount);
        if (block > 0) {
            applyToSelf(new NextTurnBlockPower(owner, block));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer) {
            qAction(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PreservationPower(owner);
    }
}
