package forsaken.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import forsaken.TheForsakenMod;

import java.util.Optional;

public class SunlightPower extends AbstractForsakenPower implements CloneablePowerInterface {
    public static final String POWER_ID = TheForsakenMod.makeID(SunlightPower.class.getSimpleName());
    private static final int HEAL_AMOUNT = 3;

    public SunlightPower(int amount) {
        super(POWER_ID, AbstractDungeon.player, amount);
        type = PowerType.BUFF;
        isTurnBased = true;

        loadRegion("regen");
        updateDescription();
    }

    private void trigger() {
        if (amount > 0) {
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            int heal = HEAL_AMOUNT;
            Optional<AbstractPower> maybeTearsOfSunlight = Optional.ofNullable(p.getPower(TearsOfSunlightPower.POWER_ID));
            if (maybeTearsOfSunlight.isPresent()) {
                heal += maybeTearsOfSunlight.get().amount;
            }
            addToTop(new ReducePowerAction(owner, owner, this, 1));
            addToTop(new HealAction(owner, owner, heal));
        } else {
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void onSpecificTrigger() {
        trigger();
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            trigger();
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SunlightPower(amount);
    }
}
