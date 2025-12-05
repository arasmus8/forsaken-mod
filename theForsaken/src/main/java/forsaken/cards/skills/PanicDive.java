package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

@SuppressWarnings("unused")
public class PanicDive extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(PanicDive.class.getSimpleName());

    public PanicDive() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = 11;
        upgradeBlockBy = 4;
        cardsToPreview = new Dazed();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qAction(new MakeTempCardInDiscardAndDeckAction(new Dazed()));
        gainBlock();
    }
}