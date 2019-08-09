package theForsaken.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theForsaken.relics.HumbleEgg;

@SpirePatch(clz = RewardItem.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.CardColor.class})
public class HumbleEggRewardItemInsertPatch {

    private static final Logger logger = LogManager.getLogger(HumbleEggRewardItemInsertPatch.class.getName());

    @SpireInsertPatch(locator = Locator.class, localvars = {"c"})
    public static void UpgradeCommonCards(RewardItem __instance, AbstractCard.CardColor ColorType, AbstractCard c) {
        logger.info(c);
        if (c.rarity == AbstractCard.CardRarity.COMMON && AbstractDungeon.player.hasRelic(HumbleEgg.ID)) {
            c.upgrade();
            logger.info("Upgraded!");
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "type");

            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}