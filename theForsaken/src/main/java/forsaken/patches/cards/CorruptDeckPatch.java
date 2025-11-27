package forsaken.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.powers.CorruptDeckPower;

@SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")
public class CorruptDeckPatch {

    @SpirePostfixPatch
    public static boolean postHasEnoughEnergyCheckPowers(boolean __result, AbstractCard __instance) {
        boolean result = __result;
        if(result) {
            int cardsPlayedThisTurn = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
            AbstractPlayer p = AbstractDungeon.player;
            if(p.hasPower(CorruptDeckPower.POWER_ID)) {
                CorruptDeckPower power = (CorruptDeckPower)p.getPower(CorruptDeckPower.POWER_ID);
                int count = power.maxCardsEachTurn;
                if(cardsPlayedThisTurn >= count) {
                    __instance.cantUseMessage = AbstractCard.TEXT[13];
                    result = false;
                }
            }
        }
        return result;
    }
}
