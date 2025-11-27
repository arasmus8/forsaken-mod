package forsaken.cards.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

import java.util.List;
import java.util.function.Consumer;

public class NobleSacrifice extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(NobleSacrifice.class.getSimpleName());

    public NobleSacrifice() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 0;
        upgradeMagicNumberBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qAction( new SelectCardsInHandAction(1, EXTENDED_DESCRIPTION[0], this::actionCallback));
    }

    private void actionCallback(List<AbstractCard> selected) {
        for(AbstractCard c : selected) {
            int cardsToDraw = c.costForTurn;
            qAction(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            if (cardsToDraw == -1) {
                cardsToDraw = EnergyPanel.getCurrentEnergy();
            }
            if (cardsToDraw > 0) {
                qAction(new DrawCardAction(cardsToDraw));
            }
        }
    }
}