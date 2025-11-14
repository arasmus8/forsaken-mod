package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;


//Double damage next turn when block is broken.

public class RipostePower extends AbstractForsakenPower implements CloneablePowerInterface {
    private static final String POWER_ID = TheForsakenMod.makeID(RipostePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean hasBlock = false;

    public RipostePower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;

        type = PowerType.BUFF;

        if (owner.currentBlock > 0) {
            hasBlock = true;
        }

        loadRegion("riposte");
        updateDescription();
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        if (MathUtils.floor(blockAmount) > 0) {
            hasBlock = true;
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        int block = owner.currentBlock;
        if (block < 1) {
            hasBlock = false;
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        // return super.onAttacked(info, damageAmount);
        if (!(info.type == DamageType.THORNS) && !(info.type == DamageType.HP_LOSS) && info.output > 0 && hasBlock) {
            int block = owner.currentBlock;
            if (block <= 0) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new PercentageBonusDamagePower(owner, 1), 1));
                hasBlock = false;
            }
        }
        return damageAmount;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new RipostePower(owner);
    }
}
