package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.actions.FunctionalAction;
import forsaken.cards.AbstractForsakenCard;

@SuppressWarnings("unused")
public class SpinAttack extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(SpinAttack.class.getSimpleName());

    public SpinAttack() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        damage = baseDamage = 7;
        upgradeDamageBy = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        qAction(spinAction());
    }

    public AbstractGameAction spinAction() {
        return new FunctionalAction(firstUpdate -> {
            AbstractMonster target = AbstractDungeon.getRandomMonster();
            dealDamage(target, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
            qAction(new DrawCardAction(1, new FunctionalAction(init -> {
                for (AbstractCard c : DrawCardAction.drawnCards) {
                    if (AbstractForsakenCard.isUnplayable(c)) {
                        qAction(spinAction());
                    }
                }
                return true;
            })));
            return true;
        });
    }
}