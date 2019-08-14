package theForsaken.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theForsaken.relics.ScalesOfTruth;

@SpirePatch(clz = AbstractRelic.class, method = "obtain")
public class ScalesOfTruthAbstractRelicPrefixPatch {

    private static final Logger logger = LogManager.getLogger(ScalesOfTruthAbstractRelicPrefixPatch.class.getName());

    @SpirePrefixPatch
    public static SpireReturn ReturnIfRelicIsScalesOfTruth(AbstractRelic __instance) {
        if (__instance.relicId == ScalesOfTruth.ID) {
            __instance.instantObtain(AbstractDungeon.player, 0, true);// 248
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;// 249
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}