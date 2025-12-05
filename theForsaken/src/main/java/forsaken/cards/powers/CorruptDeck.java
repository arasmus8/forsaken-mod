package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.CorruptDeckPower;

@SuppressWarnings("unused")
public class CorruptDeck extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(CorruptDeck.class.getSimpleName());

    public CorruptDeck() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = 4;
        upgradeMagicNumberBy = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CorruptDeckPower(p, magicNumber));
    }
}