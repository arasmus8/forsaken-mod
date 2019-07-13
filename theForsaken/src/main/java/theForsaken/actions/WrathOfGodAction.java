package theForsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class WrathOfGodAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private boolean freeToPlayOnce = false;
    private int energyOnUse = -1;
    private DamageType damageType;
    private int damage = 0;
    private int block = 0;

    public WrathOfGodAction(AbstractPlayer p, AbstractMonster m, int damage, int block, DamageType damageType, boolean freeToPlayOnce, int energyOnUse) {
        this.damageType = damageType;
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.block = block;
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

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
            }
            this.tickDuration();

            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
            this.tickDuration();

            for(int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageType), AttackEffect.FIRE));
            }
            this.tickDuration();

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

