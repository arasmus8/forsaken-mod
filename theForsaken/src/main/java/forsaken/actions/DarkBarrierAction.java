package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DarkBarrierAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private boolean freeToPlayOnce = false;
    private int energyOnUse = -1;
    private boolean isUpgraded;

    public DarkBarrierAction(AbstractPlayer p, boolean isUpgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.isUpgraded = isUpgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        int frailEffect = effect;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
            frailEffect = effect;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            frailEffect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.isUpgraded) {
            effect += 1;
            frailEffect -= 1;
        }

        if (frailEffect > 0) {
            for(int i = 0; i < frailEffect; ++i) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, 1, false), 1));
            }
        }
        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BufferPower(p, 1), 1));
            }
            this.tickDuration();

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

