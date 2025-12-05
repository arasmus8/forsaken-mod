package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.SunlightPower;

@SuppressWarnings("unused")
public class RainingSunlight extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(RainingSunlight.class.getSimpleName());

    public RainingSunlight() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        damage = baseDamage = 7;
        upgradeDamageBy = 3;
        magicNumber = baseMagicNumber = 2;
        isMultiDamage = true;
        unplayedEffect = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (dontTriggerOnUseCard) {
            qAction(new ApplyPowerAction(p, p, new SunlightPower(magicNumber), magicNumber));
            return;
        }

        dealAoeDamage(AbstractGameAction.AttackEffect.FIRE);
    }
}
