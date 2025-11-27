package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.SunlightPower;
import forsaken.powers.TearsOfSunlightPower;

public class TearsOfSunlight extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(TearsOfSunlight.class.getSimpleName());

    public TearsOfSunlight() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SunlightPower(magicNumber));
        applyToSelf(new TearsOfSunlightPower(p, 1));
    }
}