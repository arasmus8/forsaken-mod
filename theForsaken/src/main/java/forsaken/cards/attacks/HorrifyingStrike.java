package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.FearPower;

public class HorrifyingStrike extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(HorrifyingStrike.class.getSimpleName());

    public HorrifyingStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 8;
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect fx = AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
        if (damage > baseDamage * 2) {
            fx = AbstractGameAction.AttackEffect.SLASH_HEAVY;
        }
        dealDamage(m, fx);
    }

    private int fearStacks(AbstractMonster mo) {
        int fearStacks = 0;
        if (mo != null) {
            AbstractPower fearPower = mo.getPower(FearPower.POWER_ID);
            if (fearPower != null) {
                fearStacks = fearPower.amount;
            }
        }
        return fearStacks;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int originalBaseDamage = baseDamage;
        baseDamage += magicNumber * fearStacks(mo);
        super.calculateCardDamage(mo);
        baseDamage = originalBaseDamage;
        isDamageModified = damage != baseDamage;
    }

}
