package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractQuickdrawCard;

@SuppressWarnings("unused")
public class InspiringBlow extends AbstractQuickdrawCard {
    public static final String ID = TheForsakenMod.makeID(InspiringBlow.class.getSimpleName());

    public InspiringBlow() {
        super(ID, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.NONE);
        damage = baseDamage = 5;
        upgradeDamageBy = 2;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        dealAoeDamage(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (upgraded) {
            qAction(new DrawCardAction(1));
        }
    }
}