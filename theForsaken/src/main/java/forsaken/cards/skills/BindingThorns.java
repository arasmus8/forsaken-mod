package forsaken.cards.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.BindingThornsPower;

public class BindingThorns extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(BindingThorns.class.getSimpleName());

    public BindingThorns() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 5;
        upgradeMagicNumberBy = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new BindingThornsPower(m, magicNumber));
    }
}