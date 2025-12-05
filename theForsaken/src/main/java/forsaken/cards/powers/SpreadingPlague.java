package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.cards.skills.CreepingInfection;
import forsaken.powers.SpreadingPlaguePower;

@SuppressWarnings("unused")
public class SpreadingPlague extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(SpreadingPlague.class.getSimpleName());

    public SpreadingPlague() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = 3;
        cardsToPreview = new CreepingInfection();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            applyToSelf(new SpreadingPlaguePower(p));
        }
        shuffleIn(new CreepingInfection(), magicNumber);
    }
}