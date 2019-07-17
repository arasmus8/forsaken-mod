package theForsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class SpinAttackAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private static final float POST_ATTACK_WAIT_DURATION = Settings.ACTION_DUR_FASTER;
    private AbstractMonster target;
    private DamageInfo damageInfo;
    private int amount;

    public SpinAttackAction(AbstractMonster target, DamageInfo damageInfo, int amount) {
        this.duration = DURATION;
        this.target = target;
        this.damageInfo = damageInfo;
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
    }

    @Override
    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.target.currentHealth > 0) {
                this.target.damageFlash = true;
                this.target.damageFlashFrames = 4;
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                this.damageInfo.applyPowers(this.damageInfo.owner, this.target);
                this.target.damage(this.damageInfo);
                if (this.amount > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.addToTop(new SpinAttackAction(AbstractDungeon.getRandomMonster(), damageInfo, amount - 1));
                }

                AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
            }

            this.isDone = true;
        }
    }
}
