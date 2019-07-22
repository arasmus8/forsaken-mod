package theForsaken.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theForsaken.CardLibrary;
import theForsaken.TheForsakenMod;
import theForsaken.cards.DivineGuidance;

import java.util.ArrayList;
import java.util.Iterator;

public class DivineGuidanceAction extends AbstractGameAction {
    public static final String ID = TheForsakenMod.makeID(DivineGuidanceAction.class.getSimpleName());
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean toDrawPile;

    public DivineGuidanceAction(int numberOfCards, boolean toDrawPile) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.toDrawPile = toDrawPile;
    }

    public DivineGuidanceAction(int numberOfCards) {
        this(numberOfCards, false);
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
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                AbstractCard c;
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    if (this.toDrawPile) {
                        this.player.drawPile.addToRandomSpot(c);
                    } else {
                        this.player.discardPile.addToBottom(c);
                    }

                    c.lighten(false);
                    c.unhover();
                }

                for(var1 = this.player.discardPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {
                    c = (AbstractCard)var1.next();
                    c.unhover();
                    c.target_x = (float)CardGroup.DISCARD_PILE_X;
                }

                for(var1 = this.player.drawPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {
                    c = (AbstractCard)var1.next();
                    c.unhover();
                    c.target_x = CardGroup.DRAW_PILE_X;
                }

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
