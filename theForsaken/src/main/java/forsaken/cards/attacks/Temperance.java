package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

public class Temperance extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Temperance.class.getSimpleName());

    public Temperance() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        damage = baseDamage = 9;
        magicNumber = baseMagicNumber = 2;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster m, float tmp) {
        int discardedUnplayableCards = AbstractForsakenCard.unplayableCards(player.discardPile).size();
        return tmp + discardedUnplayableCards * magicNumber;
    }
}