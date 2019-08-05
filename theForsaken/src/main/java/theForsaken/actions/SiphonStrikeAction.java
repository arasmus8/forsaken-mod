package theForsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class SiphonStrikeAction extends AbstractGameAction {
    private float duration;
    private AbstractMonster target;
    private DamageInfo damageInfo;
    private int amount;

    public SiphonStrikeAction(AbstractMonster target, DamageInfo damageInfo, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.target = target;
        this.damageInfo = damageInfo;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL));
            this.target.damage(this.damageInfo);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.amount));
            }
        }

        this.tickDuration();
    }
}
