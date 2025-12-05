package forsaken.cards;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import forsaken.characters.TheForsaken;

import java.util.Optional;

import static forsaken.util.ForsakenCardTags.QUICKDRAW_CARD;

@SuppressWarnings("unused")
public abstract class AbstractQuickdrawCard extends AbstractForsakenCard {
    private static final UIStrings uiStrings;
    private static final String[] TEXT;
    private static final String cantUseString;

    public AbstractQuickdrawCard(String id, CardType type, CardRarity rarity, CardTarget target) {
        super(id, -2, type, rarity, target, TheForsaken.Enums.COLOR_GOLD, QUICKDRAW_CARD);
    }

    public AbstractQuickdrawCard(String id, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(id, -2, type, rarity, target, color);
    }

    public AbstractQuickdrawCard(String id, CardType type, CardRarity rarity, CardTarget target, CardColor color, String oldImageName) {
        super(id, -2, type, rarity, target, color, oldImageName);
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
        cantUseMessage = cantUseString;
        return !card.equals(this);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("SingleCardViewPopup");
        TEXT = uiStrings.TEXT;
        cantUseString = TEXT[13];
    }
}
