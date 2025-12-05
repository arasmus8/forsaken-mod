package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.FearPower;

@SuppressWarnings("unused")
public class HymnOfPatience extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(HymnOfPatience.class.getSimpleName());

    public HymnOfPatience() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 5;
        upgradeMagicNumberBy = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new FearPower(m, magicNumber));
    }
}
