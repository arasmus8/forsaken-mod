package forsaken.cards;

import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import forsaken.TheForsakenMod;

@SuppressWarnings("unused")
public class Recompense extends AbstractQuickdrawCard {
    public static final String ID = TheForsakenMod.makeID(Recompense.class.getSimpleName());

    public Recompense() {
        super(ID, CardType.STATUS, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        isEthereal = true;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        qAction(new LoseEnergyAction(1));
    }
}