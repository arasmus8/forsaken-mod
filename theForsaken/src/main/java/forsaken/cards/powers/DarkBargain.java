package forsaken.cards.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;
import forsaken.cards.Recompense;
import forsaken.powers.DarkBargainPower;

@SuppressWarnings("unused")
public class DarkBargain extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(DarkBargain.class.getSimpleName());

    public DarkBargain() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.NONE);
        cardsToPreview = new Recompense();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DarkBargainPower(p));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isInnate = true;
    }
}