package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class ThunderboltAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private static final float POST_ATTACK_WAIT_DURATION = Settings.ACTION_DUR_FAST;
    private AbstractMonster target;
    private DamageInfo damageInfo;
    private int amount;

    public ThunderboltAction(AbstractMonster target, DamageInfo damageInfo, int amount) {
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
                if (this.amount > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.addToBottom(new ThunderboltAction(AbstractDungeon.getRandomMonster(), damageInfo, amount - 1));
                }

                AbstractDungeon.actionManager.addToTop(new WaitAction(POST_ATTACK_WAIT_DURATION));

                AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, this.damageInfo, AttackEffect.NONE, true));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(this.target.drawX, this.target.drawY), Settings.ACTION_DUR_XFAST));
                AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }

            this.isDone = true;
        }
    }
}
