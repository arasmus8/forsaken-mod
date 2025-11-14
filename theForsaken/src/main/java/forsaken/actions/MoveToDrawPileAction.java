package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.TheForsakenMod;

public class MoveToDrawPileAction extends AbstractGameAction {
    public static final String ID = TheForsakenMod.makeID(MoveToDrawPileAction.class.getSimpleName());
    private AbstractPlayer p;
    private AbstractCard card;

    public MoveToDrawPileAction(AbstractCard card) {
        this.p = AbstractDungeon.player;
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            p.hand.moveToDeck(card, true);
            isDone = true;
        }
    }
}
