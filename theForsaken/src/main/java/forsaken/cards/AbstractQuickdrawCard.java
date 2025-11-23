package forsaken.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import static forsaken.util.ForsakenCardTags.QUICKDRAW_CARD;

public abstract class AbstractQuickdrawCard extends AbstractForsakenCard {
    private static CardTags[] append(CardTags tag, CardTags[] orig) {
        CardTags[] result = new CardTags[orig.length + 1];
        System.arraycopy(orig, 0, result, 0, orig.length);
        result[orig.length] = tag;
        return result;
    }

    public AbstractQuickdrawCard(String id, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color, CardTags ...tags) {
        super(id, cost, type, rarity, target, color, append(QUICKDRAW_CARD, tags));
    }

    public static boolean isQuickdraw(AbstractCard c) {
        return c.tags.contains(QUICKDRAW_CARD);
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        qEffect(new ShowCardBrieflyEffect(makeStatEquivalentCopy()));
    }
}
