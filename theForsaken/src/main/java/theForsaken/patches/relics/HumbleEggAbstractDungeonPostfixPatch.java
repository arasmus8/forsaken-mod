package theForsaken.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theForsaken.relics.HumbleEgg;

import java.util.ArrayList;

@SpirePatch(clz = AbstractDungeon.class, method = "getRewardCards")
public class HumbleEggAbstractDungeonPostfixPatch {

    private static final Logger logger = LogManager.getLogger(HumbleEggAbstractDungeonPostfixPatch.class.getName());

    @SpirePostfixPatch
    public static ArrayList<AbstractCard> UpgradeCommonCards(ArrayList<AbstractCard> retVal) {
        for (AbstractCard c : retVal) {
            logger.info(c);
            if (c.rarity == AbstractCard.CardRarity.COMMON && AbstractDungeon.player.hasRelic(HumbleEgg.ID)) {
                logger.info("Upgrading!");
                c.upgrade();
            }
        }
        return retVal;
    }
}