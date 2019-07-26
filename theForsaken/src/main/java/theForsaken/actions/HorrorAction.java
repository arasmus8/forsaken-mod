package theForsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theForsaken.powers.FearPower;

public class HorrorAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private boolean freeToPlayOnce;
    private int energyOnUse;
    private boolean isUpgraded;

    public HorrorAction(AbstractPlayer p, AbstractMonster m, boolean isUpgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.m = m;
        this.isUpgraded = isUpgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.isUpgraded) {
            effect += 1;
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new FearPower(m, 1, false), 1));
            }
            this.tickDuration();

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

