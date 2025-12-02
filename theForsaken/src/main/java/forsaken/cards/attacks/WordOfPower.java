package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

import java.util.List;

public class WordOfPower extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(WordOfPower.class.getSimpleName());

    public WordOfPower() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 10;
        magicNumber = baseMagicNumber = 2;
        upgradeMagicNumberBy = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        List<AbstractCard> unplayableCards = unplayableCards(true, true, true);
        return tmp + magicNumber * unplayableCards.size();
    }
}