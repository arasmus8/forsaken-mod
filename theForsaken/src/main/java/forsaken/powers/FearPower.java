package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;


//Reduce damage dealt by 1

public class FearPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(FearPower.class.getSimpleName());

    public FearPower(final AbstractCreature owner, final int amount) {
        super(POWER_ID, owner, amount);

        type = PowerType.DEBUFF;
        isTurnBased = true;
        priority = 10;

        loadRegion("fear");
        updateDescription();
    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }

    public float atDamageGive(float damage, DamageType type) {
        if (type == DamageType.NORMAL) {
            if (damage >= 1.0F) {
                return damage - 1.0f * amount;
            }
        }
        return damage;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type != DamageType.THORNS && info.type != DamageType.HP_LOSS) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            flash();
            qAction(new ReducePowerAction(owner, owner, this, 1));
        }

    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FearPower(owner, amount);
    }
}
