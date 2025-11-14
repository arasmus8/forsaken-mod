package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.oldCards.ShieldBash;

import java.util.Iterator;

public class ShieldBashAction extends AbstractGameAction {
    private AbstractCard card;

    public ShieldBashAction(AbstractCard card, int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.amount = amount;
    }

    private void growCardPower(AbstractCard c, int amount) {
        if (c instanceof ShieldBash) {
            c.baseDamage += amount;
            c.baseBlock += amount;
            c.applyPowers();
        }
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            growCardPower(this.card, this.amount);
            this.card.applyPowers();
            Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();

            AbstractCard c;
            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                growCardPower(c, this.amount);
            }

            var1 = AbstractDungeon.player.drawPile.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                growCardPower(c, this.amount);
            }

            var1 = AbstractDungeon.player.hand.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                growCardPower(c, this.amount);
            }
        }

        this.tickDuration();
    }
}
