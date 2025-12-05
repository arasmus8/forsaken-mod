package forsaken.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.powers.SunlightPower;

@SuppressWarnings("unused")
public class Smite extends AbstractQuickdrawCard {
    public static final String ID = TheForsakenMod.makeID(Smite.class.getSimpleName());

    public Smite() {
        super(ID, CardType.ATTACK, CardRarity.BASIC, CardTarget.ALL_ENEMY);
        baseDamage = damage = 4;
        upgradeDamageBy = 2;
        isMultiDamage = true;
        baseMagicNumber = magicNumber = 1;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        dealAoeDamage(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        qAction(new ApplyPowerAction(p, p, new SunlightPower(magicNumber), magicNumber));
    }

}
