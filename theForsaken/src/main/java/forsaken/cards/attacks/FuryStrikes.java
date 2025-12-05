package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

@SuppressWarnings("unused")
public class FuryStrikes extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(FuryStrikes.class.getSimpleName());

    public FuryStrikes() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 2;
        upgradeDamageBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 3; i++) {
            dealDamage(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        }
    }
}
