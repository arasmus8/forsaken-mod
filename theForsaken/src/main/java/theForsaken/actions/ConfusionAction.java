package theForsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

public class ConfusionAction extends AbstractGameAction {
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private static final int ATTACK_TIMES = 3;
    private AbstractMonster target;

    public ConfusionAction(AbstractMonster target) {
        this.duration = DURATION;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.BLUNT_HEAVY;
    }

    @Override
    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.target.intent == Intent.ATTACK || this.target.intent == Intent.ATTACK_BUFF || this.target.intent == Intent.ATTACK_DEBUFF || this.target.intent == Intent.ATTACK_DEFEND) {
                int damage = this.target.getIntentBaseDmg();
                for (int i = 0; i < ATTACK_TIMES; ++i) {
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    if (m != null) {
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(this.target, damage, DamageInfo.DamageType.NORMAL), this.attackEffect));
                    }
                }
            }

            this.isDone = true;
        }
    }
}
