package forsaken.actions;

import basemod.interfaces.CloneablePowerInterface;
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
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;

public class PurificationAction extends AbstractGameAction {
    private final AbstractMonster target;
    private final float duration;
    private final int damage;
    private final DamageType damageType;

    public PurificationAction(AbstractMonster target, int damage, DamageType damageType) {
        this.duration = Settings.ACTION_DUR_MED;
        this.target = target;
        this.damage = damage;
        this.damageType = damageType;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            AbstractPlayer p = AbstractDungeon.player;
            AbstractMonster m = this.target;
            if (this.target != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), Settings.ACTION_DUR_MED));
            }

            AbstractDungeon.player.powers.stream()
                    .filter(pow -> pow.type.equals(AbstractPower.PowerType.DEBUFF))
                    .forEach(power -> {
                        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, power));
                        if (power instanceof CloneablePowerInterface) {
                            AbstractPower copy = ((CloneablePowerInterface) power).makeCopy();
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, copy, copy.amount));
                        } else if(power.ID.equals(PoisonPower.POWER_ID)) {
                            AbstractPower copy = new PoisonPower(m, p, power.amount);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, copy, copy.amount));
                        } else if(power.ID.equals(WeakPower.POWER_ID)){
                            AbstractPower copy = new WeakPower(m, power.amount, false);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, copy, copy.amount));
                        } else if(power.ID.equals(VulnerablePower.POWER_ID)){
                            AbstractPower copy = new VulnerablePower(m, power.amount, false);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, copy, copy.amount));
                        }
                    });

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageType)));
            this.isDone = true;
        }

        this.tickDuration();
    }
}
