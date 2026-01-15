package forsaken.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import forsaken.TheForsakenMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlackenedSunEmblem extends AbstractForsakenRelic implements OnReceivePowerRelic {
    public static final String ID = TheForsakenMod.makeID(BlackenedSunEmblem.class.getSimpleName());
    private static final int VIGOR_AMOUNT = 3;
    private static final Logger logger = LogManager.getLogger(BlackenedSunEmblem.class.getName());

    public BlackenedSunEmblem() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {
        if (
                power.ID.equals(WeakPower.POWER_ID)
                || power.ID.equals(VulnerablePower.POWER_ID)
                || power.ID.equals(PoisonPower.POWER_ID)
        ) {
            AbstractCreature p = AbstractDungeon.player;
            flash();
            logger.info("BlackenedSunEmblem power: {}, stackAmount: {}", power.name, stackAmount);
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, VIGOR_AMOUNT), VIGOR_AMOUNT));
        }

        return stackAmount;
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature source) {
        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + VIGOR_AMOUNT + DESCRIPTIONS[1];
    }
}
