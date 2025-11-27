package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
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
        priority = 10;

        loadRegion("fear");
        updateDescription();
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
        if (card.type == AbstractCard.CardType.ATTACK){
            boolean hitsAllEnemies = card.target == AbstractCard.CardTarget.ALL_ENEMY;
            boolean targetsPowerOwner =  action != null && action.target != null && action.target.equals(owner);
            if( hitsAllEnemies || targetsPowerOwner) {
                flash();
                qAction(new ReducePowerAction(owner, owner, this, 1));
            }
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
