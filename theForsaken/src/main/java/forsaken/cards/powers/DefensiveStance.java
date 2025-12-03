package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.powers.DefensiveStancePower;

public class DefensiveStance extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(DefensiveStance.class.getSimpleName());

    public DefensiveStance() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        upgradeCostTo = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DefensiveStancePower(p, 1));
    }

}