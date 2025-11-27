package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractQuickdrawCard;

public class ShieldCharge extends AbstractQuickdrawCard {
    public static final String ID = TheForsakenMod.makeID(ShieldCharge.class.getSimpleName());

    public ShieldCharge() {
        super(ID, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 4;
        upgradeDamageBy = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (upgraded) {
            qAction(new DrawCardAction(1));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        applyPowers();
        AbstractMonster target = AbstractDungeon.getRandomMonster();
        new UseCardAction(this, target);
        use(AbstractDungeon.player, target);
    }
}
