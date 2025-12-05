package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import forsaken.TheForsakenMod;

public class NobleSacrificeAction extends AbstractGameAction {
    public static final String ID = TheForsakenMod.makeID(NobleSacrificeAction.class.getSimpleName());
    private final AbstractPlayer p;
    private final int amount;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public NobleSacrificeAction(int amount) {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                if (amount > 0) {
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.p, this.amount));
                }
            } else if (this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();

                int cardsToDraw = Math.max(c.costForTurn, 0) + this.amount;
                if (c.costForTurn == -1) {// 49
                    cardsToDraw = EnergyPanel.getCurrentEnergy() + this.amount;
                }
                if (cardsToDraw > 0) {
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.p, cardsToDraw));
                }
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, this.p.hand));
                AbstractDungeon.player.hand.refreshHandLayout();
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    int cardsToDraw = Math.max(c.costForTurn, 0) + this.amount;
                    if (c.costForTurn == -1) {// 49
                        cardsToDraw = EnergyPanel.getCurrentEnergy() + this.amount;
                    }
                    if (cardsToDraw > 0) {
                        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.p, cardsToDraw));
                    }
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.handCardSelectScreen.selectedCards));
                }

                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
