package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.cards.skills.Parry;
import forsaken.powers.RipostePower;

public class Riposte extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Riposte.class.getSimpleName());

    public Riposte() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        magicNumber = baseMagicNumber = 1;
        upgradeMagicNumberBy = 1;
        cardsToPreview = new Parry();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        shuffleIn(new Parry(), magicNumber);
        applyToSelf(new RipostePower(p, 1));
    }
}