package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.FearPower;

public class Enfeeble extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Enfeeble.class.getSimpleName());

    public Enfeeble() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 3;
        magicNumber = baseMagicNumber = 8;
        upgradeMagicNumberBy = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m == null || m.isDeadOrEscaped()) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
        applyToEnemy(m, new FearPower(m, magicNumber));
    }
}