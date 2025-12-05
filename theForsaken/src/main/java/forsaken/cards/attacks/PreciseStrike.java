package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

@SuppressWarnings("unused")
public class PreciseStrike extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(PreciseStrike.class.getSimpleName());

    public PreciseStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 8;
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        if (upgraded) {
            applyToEnemy(m, new WeakPower(m, magicNumber, false));
        }
    }
}