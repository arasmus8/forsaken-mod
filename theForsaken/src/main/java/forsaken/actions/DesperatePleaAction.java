package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class DesperatePleaAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private int amount;


    public DesperatePleaAction(AbstractPlayer player, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.player = player;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            for(AbstractPower power : this.player.powers) {
                if (power.type == AbstractPower.PowerType.DEBUFF) {
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.player, this.player, power.ID));
                }
            }

            AbstractPower power = new StrengthPower(this.player, this.amount);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.player, this.player, power, this.amount));
            this.isDone = true;
        }

        this.tickDuration();
    }
}
