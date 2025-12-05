package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.BolsterPower;

@SuppressWarnings("unused")
public class Bolster extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Bolster.class.getSimpleName());

    public Bolster() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BolsterPower(p, magicNumber));
    }
}