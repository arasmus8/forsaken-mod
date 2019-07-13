package theForsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.MetallicizeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class InspiringBlowAction extends AbstractGameAction {
    private float duration;
    private AbstractMonster target;
    private DamageInfo damageInfo;
    private int amount;

    public InspiringBlowAction(AbstractMonster target, DamageInfo damageInfo, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.target = target;
        this.damageInfo = damageInfo;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_DIAGONAL));
            this.target.damage(this.damageInfo);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, this.amount), this.amount));
            }
        }

        this.tickDuration();
    }
}
