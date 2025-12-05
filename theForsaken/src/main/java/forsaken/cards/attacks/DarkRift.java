package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

@SuppressWarnings("unused")
public class DarkRift extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(DarkRift.class.getSimpleName());

    public DarkRift() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        exhaust = true;
        isMultiDamage = true;
        damage = baseDamage = 6;
        upgradeDamageBy = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealAoeDamage(AbstractGameAction.AttackEffect.FIRE);
        for(AbstractPower pow : p.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF) {
                dealAoeDamage(AbstractGameAction.AttackEffect.FIRE);
            }
        }
    }
}