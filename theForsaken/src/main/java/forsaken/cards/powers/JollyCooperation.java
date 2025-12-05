package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.characters.TheForsaken;
import forsaken.powers.JollyCooperationPower;

@SuppressWarnings("unused")
public class JollyCooperation extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(JollyCooperation.class.getSimpleName());

    public JollyCooperation() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE, TheForsaken.Enums.COLOR_GOLD, "WordsOfMight");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new JollyCooperationPower());
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isInnate = true;
    }
}
