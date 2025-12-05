package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

@SuppressWarnings("unused")
public class SacredOath extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(SacredOath.class.getSimpleName());

    public SacredOath() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
        block = baseBlock = 12;
        upgradeBlockBy = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        gainBlock();
    }
}