package forsaken.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import forsaken.TheForsakenMod;

public class FadingSunlight extends AbstractForsakenRelic {
    public static final String ID = TheForsakenMod.makeID(FadingSunlight.class.getSimpleName());
    private final static int MAX_HP_LOSS = 5;

    public FadingSunlight() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onVictory() {
        boolean isEliteOrBoss = AbstractDungeon.getCurrRoom().eliteTrigger;
        if (isEliteOrBoss) {
            flash();
            AbstractDungeon.player.decreaseMaxHealth(MAX_HP_LOSS);
        }
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }
}
