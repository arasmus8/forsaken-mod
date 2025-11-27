package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.CardLibrary;
import forsaken.TheForsakenMod;
import forsaken.oldCards.DivineGuidance;

public class DivineGuidanceAction extends AbstractGameAction {
    public static final String ID = TheForsakenMod.makeID(DivineGuidanceAction.class.getSimpleName());
    public static final String[] TEXT;
    private final int numberOfCards;
    private final boolean toDrawPile;

    public DivineGuidanceAction(int numberOfCards, boolean toDrawPile) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.numberOfCards = numberOfCards;
        this.toDrawPile = toDrawPile;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.numberOfCards > 0) {
                String tipMessage;
                if (this.toDrawPile) {
                    tipMessage = numberOfCards > 1 ? TEXT[2] + this.numberOfCards + TEXT[4] : TEXT[1];
                } else {
                    tipMessage = numberOfCards > 1 ? TEXT[2] + this.numberOfCards + TEXT[3] : TEXT[0];
                }
                CardLibrary library = new CardLibrary(false, false, false, DivineGuidance.ID);

                AbstractDungeon.gridSelectScreen.open(library.cardGroup, this.numberOfCards, tipMessage, false);

                this.tickDuration();
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                AbstractDungeon.gridSelectScreen.selectedCards.forEach(c -> {
                    if (toDrawPile) {
                        AbstractDungeon.player.drawPile.addToRandomSpot(c);
                    } else {
                        AbstractDungeon.player.discardPile.addToBottom(c);
                    }

                    c.lighten(false);
                    c.unhover();
                });

                AbstractDungeon.player.discardPile.group.forEach(c -> {
                    c.unhover();
                    c.target_x = (float)CardGroup.DISCARD_PILE_X;
                });

                AbstractDungeon.player.drawPile.group.forEach(c -> {
                    c.unhover();
                    c.target_x = CardGroup.DRAW_PILE_X;
                });

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    }
}
