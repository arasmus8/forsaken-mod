package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import forsaken.TheForsakenMod;

public class DefensiveStancePower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(DefensiveStancePower.class.getSimpleName());

    public DefensiveStancePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        type = PowerType.BUFF;

        loadRegion("dexterity");
        updateDescription();
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return (1f + 0.25f * amount) * blockAmount;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return Math.max(0f, 1 - (0.25f * amount)) * damage;
        }
        return damage;
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target.equals(owner) && power.ID.equals(FrailPower.POWER_ID)) {
            flash();
            addToTop(new RemoveSpecificPowerAction(owner, owner, FrailPower.POWER_ID));
        }
    }

    @Override
    public void onInitialApplication() {
        AbstractPower power = owner.getPower(FrailPower.POWER_ID);
        if (power != null) {
            flash();
            addToTop(new RemoveSpecificPowerAction(owner, owner, FrailPower.POWER_ID));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount * 25 + "%" +
                DESCRIPTIONS[1] +
                amount * 25 + "%" +
                DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new DefensiveStancePower(owner, amount);
    }
}
