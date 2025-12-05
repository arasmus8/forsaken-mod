package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;


public class BindingThornsPower extends AbstractForsakenPower implements CloneablePowerInterface {
    private static final String POWER_ID = TheForsakenMod.makeID(BindingThornsPower.class.getSimpleName());

    public BindingThornsPower(final AbstractCreature owner, final int amount) {
        super(POWER_ID, owner, amount);

        type = PowerType.DEBUFF;

        this.loadRegion("binding_thorns");

        updateDescription();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type != DamageType.THORNS && info.type != DamageType.HP_LOSS) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(owner, owner, this, 1));
            AbstractDungeon.actionManager.addToTop(new DamageAction(owner, new DamageInfo(owner, amount, DamageType.THORNS)));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BindingThornsPower(owner, amount);
    }
}
