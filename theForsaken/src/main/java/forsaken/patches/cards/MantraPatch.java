package forsaken.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import forsaken.TheForsakenMod;
import javassist.CtBehavior;

@SpirePatch2(
        clz = CardGroup.class,
        method = "initializeDeck"
)
public class MantraPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"c"}
    )
    public static void Insert(AbstractCard c) {
        if (TheForsakenMod.mantraInnateManager.cardIsMantraInnate(c)) {
            c.isInnate = true;
        }
    }

    private static class Locator extends SpireInsertLocator {
        public Locator() {
        }

        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

            // We want to find the call to this.addToTop(c) INSIDE the second loop.
            Matcher cardFieldAccessMatcher =
                    new Matcher.FieldAccessMatcher(AbstractCard.class, "isInnate");

            // Return the line index where this call occurs
            return LineFinder.findInOrder(ctMethodToPatch, cardFieldAccessMatcher);
        }
    }
}
