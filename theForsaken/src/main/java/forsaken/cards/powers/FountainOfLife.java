package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.characters.TheForsaken;
import forsaken.powers.FountainOfLifePower;

public class FountainOfLife extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(FountainOfLife.class.getSimpleName());

    public FountainOfLife() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.NONE, TheForsaken.Enums.COLOR_GOLD, CardTags.HEALING);
        magicNumber = baseMagicNumber = 3;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FountainOfLifePower(p, magicNumber));
    }
}