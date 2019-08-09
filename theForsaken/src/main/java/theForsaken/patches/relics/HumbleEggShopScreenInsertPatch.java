package theForsaken.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theForsaken.relics.HumbleEgg;

@SpirePatch(clz = ShopScreen.class, method = "initCards")
public class HumbleEggShopScreenInsertPatch {

    private static final Logger logger = LogManager.getLogger(HumbleEggShopScreenInsertPatch.class.getName());

    @SpireInsertPatch(locator = Locator.class, localvars = {"c"})
    public static void UpgradeCommonCards(ShopScreen __instance, AbstractCard c) {
        logger.info(c);
        if (c.rarity == AbstractCard.CardRarity.COMMON && AbstractDungeon.player.hasRelic(HumbleEgg.ID)) {
            logger.info("Upgrading!");
            c.upgrade();
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "target_x");

            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}