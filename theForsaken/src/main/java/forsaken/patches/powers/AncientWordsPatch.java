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
import forsaken.relics.WornEffigy;
import javassist.CtBehavior;

import java.util.function.BiPredicate;

@SuppressWarnings("unused")
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
                BiPredicate<Boolean, AbstractCreature> damageRandom = alsoDamageRandomEnemyAction(__instance, __instance.amount);
                for (int i = 0; i < target.getPower(AncientWordsPower.POWER_ID).amount; i++) {
                    AbstractDungeon.actionManager.addToTop(new BiFunctionalAction<>(damageRandom, target));
                }
            }
            if (__instance.target.equals(AbstractDungeon.player)) {
                if (AbstractDungeon.player.hasRelic(WornEffigy.ID)) {
                    AbstractDungeon.player.getRelic(WornEffigy.ID).onTrigger();
                }
            }
        }
    }

    private static BiPredicate<Boolean, AbstractCreature> alsoDamageRandomEnemyAction(PoisonLoseHpAction __instance, int damageAmount) {
        return (firstUpdate, creature) -> {
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
