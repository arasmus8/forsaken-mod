package forsaken.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import forsaken.TheForsakenMod;
import forsaken.cards.AbstractForsakenCard;

import java.util.Optional;

public class QuickAttack extends AbstractForsakenCard {
    public static final String ID = TheForsakenMod.makeID(QuickAttack.class.getSimpleName());

    public QuickAttack() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 4;
        upgradeDamageBy = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dealDamage(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Optional<AbstractCard> topUnplayableCard = AbstractForsakenCard.unplayableCards(p.drawPile).stream()
                .limit(1)
                .findFirst();
        topUnplayableCard.ifPresent(c -> {
            p.drawPile.removeCard(c);
            p.drawPile.addToTop(c);
            qAction(new DrawCardAction(1));
        });
    }
}
