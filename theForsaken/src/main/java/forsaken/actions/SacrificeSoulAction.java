package forsaken.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;

import java.util.Iterator;

public class SacrificeSoulAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private float duration;
    private int[] damage;
    private int bonusDamage;
    private DamageType damageType;

    public SacrificeSoulAction(AbstractPlayer player, int[] damage, int bonusDamage, DamageType damageType) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.player = player;
        this.damage = damage;
        this.damageType = damageType;
        this.bonusDamage = bonusDamage;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else if (this.duration == Settings.ACTION_DUR_XFAST) {
            Iterator powerIterator = this.player.powers.iterator();
            int buffsRemoved = 0;

            while(powerIterator.hasNext()) {
                AbstractPower p = (AbstractPower)powerIterator.next();
                if (p.type == AbstractPower.PowerType.BUFF) {
                    AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(player, player, p.ID));
                    buffsRemoved += 1;
                }
            }

            int[] damageModified = new int[damage.length];
            for(int i=0; i<damage.length; i++) {
                damageModified[i] = damage[i] + bonusDamage * buffsRemoved;
            }

            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m != null) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new DarkOrbActivateEffect(m.hb.cX, m.hb.cY), Settings.ACTION_DUR_XFAST));
                }
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, damageModified, damageType, AttackEffect.FIRE));
            this.isDone = true;
        }

        this.tickDuration();
    }
}
