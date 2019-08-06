package theForsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class DevotionAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    public static int numPlaced;

    public DevotionAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.target = target;
        this.p = (AbstractPlayer) target;
        this.setValues(target, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == 0.5F) {
            if (this.p.hand.size() < this.amount) {
                this.amount = this.p.hand.size();
            }

            int i;
            if (this.p.hand.group.size() > this.amount) {
                numPlaced = this.amount;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
                this.tickDuration();
                return;
            }

            for (i = 0; i < this.p.hand.size(); ++i) {
                this.p.hand.moveToDeck(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng), false);
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (c.canUpgrade()) {
                    c.superFlash();
                    c.upgrade();
                }
                this.p.hand.moveToDeck(c, false);
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("PutOnDeckAction");
        TEXT = uiStrings.TEXT;
    }
}
