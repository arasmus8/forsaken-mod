package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractQuickdrawCard;

@SuppressWarnings("unused")
public class ShieldCharge extends AbstractQuickdrawCard {
    public static final String ID = TheForsakenMod.makeID(ShieldCharge.class.getSimpleName());

    public ShieldCharge() {
        super(ID, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 4;
        upgradeDamageBy = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        qAction(new FunctionalAction(firstUpdate -> {
            if (m != null) {
                DamageInfo info = makeDamageInfo(damage, damageTypeForTurn);
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.BLUNT_LIGHT, false));
                m.damage(info);
                int block = Math.max(0, m.lastDamageTaken);
                if (block > 0) {
                    addToTop(new GainBlockAction(p, p, block));
                }
            }
            return true;
        }));
    }
}
