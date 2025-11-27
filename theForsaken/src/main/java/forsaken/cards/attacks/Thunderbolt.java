package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;

public class Thunderbolt extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(Thunderbolt.class.getSimpleName());

    public Thunderbolt() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 5;
        upgradeDamageBy = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), Settings.ACTION_DUR_XFAST));
        addToBot(new SFXAction("ORB_LIGHTNING_EVOKE"));
        dealDamage(m, AbstractGameAction.AttackEffect.NONE);
        qAction(new FunctionalAction(firstUpdate -> {
            AbstractMonster randomTarget = AbstractDungeon.getRandomMonster();
            if (randomTarget != null) {
                addToBot(new SFXAction("ORB_LIGHTNING_EVOKE"));
                addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), Settings.ACTION_DUR_XFAST));
                dealDamage(randomTarget, AbstractGameAction.AttackEffect.NONE);
            }
            return true;
        }));
    }
}
