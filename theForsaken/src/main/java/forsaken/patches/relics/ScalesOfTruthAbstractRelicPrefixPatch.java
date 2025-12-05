package forsaken.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import forsaken.relics.ScalesOfTruth;

import java.util.Objects;

@SpirePatch(clz = AbstractRelic.class, method = "obtain")
public class ScalesOfTruthAbstractRelicPrefixPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> ReturnIfRelicIsScalesOfTruth(AbstractRelic __instance) {
        if (Objects.equals(__instance.relicId, ScalesOfTruth.ID)) {
            __instance.instantObtain(AbstractDungeon.player, 0, true);// 248
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;// 249
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}