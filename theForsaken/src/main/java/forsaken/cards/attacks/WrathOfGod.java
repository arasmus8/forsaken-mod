package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import forsaken.TheForsakenMod;
import forsaken.actions.BiFunctionalAction;
import forsaken.actions.XCostAction;
import forsaken.cards.AbstractForsakenCard;

public class WrathOfGod extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(WrathOfGod.class.getSimpleName());

    private int timesToDealDamage = 0;

    public WrathOfGod() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        damage = baseDamage = 7;
        upgradeDamageBy = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qAction(new XCostAction<>(this, this::xAction, p));
    }

    private boolean xAction(int x, AbstractCreature source) {
        timesToDealDamage = x;
        if (timesToDealDamage > 0) {
            qAction(new SFXAction("RAGE", 0.9f, true));
            qAction(new VFXAction(new ShockWaveEffect(source.hb.cX, source.hb.cY, TheForsakenMod.FORSAKEN_GOLD, ShockWaveEffect.ShockWaveType.ADDITIVE)));
            qAction(new BiFunctionalAction<>(Settings.ACTION_DUR_FAST, this::dealDamageAndGainBlock, source));
        }
        return true;
    }

    private boolean dealDamageAndGainBlock(boolean firstUpdate, AbstractCreature source) {
        timesToDealDamage -= 1;
        AbstractMonster target = AbstractDungeon.getRandomMonster();
        if (target == null) return true;

        AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AbstractGameAction.AttackEffect.BLUNT_LIGHT, false));
        target.damage(makeDamageInfo(damage, damageTypeForTurn));
        if (target.lastDamageTaken > 0) {
            addToTop(new GainBlockAction(source, target.lastDamageTaken));
            // if (target.hb != null) {
                // TODO: VFX
            // }
        }
        return timesToDealDamage < 1;
    }
}