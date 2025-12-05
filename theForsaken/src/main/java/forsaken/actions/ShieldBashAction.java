package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.oldCards.ShieldBash;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ShieldBashAction extends AbstractGameAction {
    private final AbstractCard card;

    public ShieldBashAction(AbstractCard card, int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            isDone = true;
            Predicate<AbstractCard> isShieldBash = c -> c instanceof ShieldBash;
            Consumer<AbstractCard> growCardPower = c -> {
                c.baseDamage += amount;
                c.baseBlock += amount;
                c.applyPowers();
            };
            growCardPower.accept(card);
            card.applyPowers();

            AbstractDungeon.player.discardPile.group.stream()
                    .filter(isShieldBash)
                    .forEach(growCardPower);

            AbstractDungeon.player.hand.group.stream()
                    .filter(isShieldBash)
                    .forEach(growCardPower);

            AbstractDungeon.player.drawPile.group.stream()
                    .filter(isShieldBash)
                    .forEach(growCardPower);

            AbstractDungeon.player.exhaustPile.group.stream()
                    .filter(isShieldBash)
                    .forEach(growCardPower);
        }

        this.tickDuration();
    }
}
