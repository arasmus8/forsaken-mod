package forsaken.patches.powers;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.actions.BiFunctionalAction;
import forsaken.powers.AncientWordsPower;
import javassist.CtBehavior;

import java.util.function.BiPredicate;

@SpirePatch2(
        clz = PoisonLoseHpAction.class,
        method = "update"
)
public class AncientWordsPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(PoisonLoseHpAction __instance) {
        if (__instance.target != null) {
            AbstractCreature target = __instance.target;
            if (target.hasPower(AncientWordsPower.POWER_ID)) {
                int damageAmount = __instance.amount;
                BiPredicate<Boolean, AbstractCreature> damageRandom = (firstUpdate, creature) -> {
                    if (creature.hasPower(AncientWordsPower.POWER_ID)) {
                        AbstractPower p = creature.getPower(AncientWordsPower.POWER_ID);
                        p.flash();
                        AbstractMonster t = AbstractDungeon.getRandomMonster();
                        if (t != null) {
                            AbstractDungeon.actionManager.addToTop(new LoseHPAction(t, creature, damageAmount));
                        }
                    }
                    return true;
                };
                AbstractDungeon.actionManager.addToBottom(new BiFunctionalAction<>(damageRandom, target));
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        public Locator() {}

        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            // find the call to AbstractCreature.damage
            Matcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "damage");

            return LineFinder.findInOrder(ctBehavior, methodCallMatcher);
        }
    }
}
