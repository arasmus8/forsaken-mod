package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.SunlightPower;

public class BountifulSunlight extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(BountifulSunlight.class.getSimpleName());

    public BountifulSunlight() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 8;
        upgradeMagicNumberBy = 4;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SunlightPower(magicNumber));
    }
}