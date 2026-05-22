package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.MantraPower;
import forsaken.powers.UpgradedMantraPower;

@SuppressWarnings("unused")
public class Mantra extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Mantra.class.getSimpleName());

    public Mantra() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            applyToSelf(new UpgradedMantraPower());
        } else {
            applyToSelf(new MantraPower());
        }
    }
}