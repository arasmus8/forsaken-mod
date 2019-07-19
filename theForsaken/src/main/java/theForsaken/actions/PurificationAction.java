package theForsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;

import java.util.Iterator;

public class PurificationAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final AbstractMonster target;
    private float duration;
    private int damage;
    private DamageType damageType;

    public PurificationAction(AbstractPlayer player, AbstractMonster target, int damage, DamageType damageType) {
        this.duration = Settings.ACTION_DUR_MED;
        this.player = player;
        this.target = target;
        this.damage = damage;
        this.damageType = damageType;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            AbstractMonster m = this.target;
            AbstractPlayer p = this.player;
            if (this.target != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), Settings.ACTION_DUR_MED));
            }

            Iterator powerIterator = this.player.powers.iterator();
            while(powerIterator.hasNext()) {
                AbstractPower power = (AbstractPower)powerIterator.next();
                if (power.type == AbstractPower.PowerType.DEBUFF) {
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(player, player, power.ID));
                    power.owner = m;
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, power, power.amount));
                }
            }

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageType)));
            this.isDone = true;
        }

        this.tickDuration();
    }
}
