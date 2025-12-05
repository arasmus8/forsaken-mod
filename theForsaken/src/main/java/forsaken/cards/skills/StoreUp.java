package forsaken.cards.skills;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;

@SuppressWarnings("unused")
public class StoreUp extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(StoreUp.class.getSimpleName());

    public StoreUp() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        block = baseBlock = 3;
        upgradeBlockBy = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qAction(new DrawCardAction(1, new FunctionalAction(firstUpdate -> {
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (AbstractForsakenCard.isUnplayable(c)) {
                    gainBlock();
                }
            }
            return true;
        })));
    }
}