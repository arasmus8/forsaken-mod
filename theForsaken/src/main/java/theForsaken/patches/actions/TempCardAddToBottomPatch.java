package theForsaken.patches.actions;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theForsaken.cards.AbstractForsakenCard;

@SpirePatch(clz = CardGroup.class, method = "addToBottom")

public class TempCardAddToBottomPatch {

    @SpirePostfixPatch
    public static void postAddCard(CardGroup __instance, AbstractCard card) {
        if (CardCrawlGame.isInARun() &&
                AbstractDungeon.isPlayerInDungeon() &&
                AbstractDungeon.player != null &&
                AbstractDungeon.player.hand != null
        ) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof AbstractForsakenCard) {
                    ((AbstractForsakenCard) c).triggerWhenCardAddedInCombat();
                }
            }
        }
    }
}
