package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.PreservationPower;

public class Preservation extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Preservation.class.getSimpleName());

    public Preservation() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        block = baseBlock = 0;
        upgradeBlockBy = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PreservationPower(p));
        if (upgraded) {
            gainBlock();
        }
    }
}