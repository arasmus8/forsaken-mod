package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

public class BlindFaith extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(BlindFaith.class.getSimpleName());

    public BlindFaith() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int unplayablesInHand = AbstractForsakenCard.unplayableCards(p.hand).size();
        qAction(new DiscardAction(p, p, p.hand.size(), true));
        qAction(new DrawCardAction(unplayablesInHand));
    }
}