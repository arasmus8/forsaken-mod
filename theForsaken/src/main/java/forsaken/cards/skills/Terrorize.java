package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.FearPower;

@SuppressWarnings("unused")
public class Terrorize extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Terrorize.class.getSimpleName());

    public Terrorize() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        exhaust = true;
        magicNumber = baseMagicNumber = 15;
        upgradeMagicNumberBy = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new FearPower(m, magicNumber));
    }
}