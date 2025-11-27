package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.PrudencePower;

public class Prudence extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Prudence.class.getSimpleName());

    public Prudence() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = 2;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PrudencePower(p, magicNumber));
    }
}