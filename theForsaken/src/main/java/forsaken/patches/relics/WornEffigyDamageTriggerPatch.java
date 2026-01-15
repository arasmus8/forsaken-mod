package forsaken.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.relics.WornEffigy;
import javassist.CtBehavior;

@SuppressWarnings("unused")
@SpirePatch2(
        clz = PoisonLoseHpAction.class,
        method = "update"
)
public class WornEffigyDamageTriggerPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractGameAction __instance) {
        if (__instance.target.equals(AbstractDungeon.player)) {
            if (AbstractDungeon.player.hasRelic(WornEffigy.ID)) {
                AbstractDungeon.player.getRelic(WornEffigy.ID).onTrigger();
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        public Locator() {}

        public int[] Locate(CtBehavior ctMethod) throws Exception {
            // We want to find the call to `this.target.damage`
            Matcher targetDamageCallMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "damage");

            return LineFinder.findInOrder(ctMethod, targetDamageCallMatcher);
        }
    }
}
