package forsaken.cards;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import forsaken.characters.TheForsaken;

import java.util.Optional;

import static forsaken.util.ForsakenCardTags.QUICKDRAW_CARD;

public abstract class AbstractQuickdrawCard extends AbstractForsakenCard {
//    private static CardTags[] append(CardTags tag, CardTags[] orig) {
//        CardTags[] result = new CardTags[orig.length + 1];
//        System.arraycopy(orig, 0, result, 0, orig.length);
//        result[orig.length] = tag;
//        return result;
//    }

    public AbstractQuickdrawCard(String id, CardType type, CardRarity rarity, CardTarget target) {
        super(id, -2, type, rarity, target, TheForsaken.Enums.COLOR_GOLD, QUICKDRAW_CARD);
    }

    public AbstractQuickdrawCard(String id, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(id, -2, type, rarity, target, color);
    }

    public static boolean isQuickdraw(AbstractCard c) {
        return c.tags.contains(QUICKDRAW_CARD);
    }

    public static Optional<AbstractCard> nextQuickdrawCard() {
        return AbstractDungeon.player.drawPile.group.stream()
                .filter(AbstractQuickdrawCard::isQuickdraw)
                .findFirst();
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        qEffect(new ShowCardBrieflyEffect(makeStatEquivalentCopy()));
        // Triggers things like onUseCard
        new UseCardAction(this);
        use(AbstractDungeon.player, AbstractDungeon.getRandomMonster());
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return !card.equals(this);
    }
}
